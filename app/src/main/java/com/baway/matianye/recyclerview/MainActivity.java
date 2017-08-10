package com.baway.matianye.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener{
    private XRecyclerView recyclerView;
    private List<Data.DataBean.ReturnDataBean> list = new ArrayList<>();
    private List<Data.DataBean.ReturnDataBean.ComicsBean> mlist = new ArrayList<>();
    private List<String> mlist1 = new ArrayList<>();
    private RecyclerAdapter adapter;
    private  int page = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (XRecyclerView) findViewById(R.id.xrecyclerview);
        LoadData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        adapter = new RecyclerAdapter(MainActivity.this, mlist);
        recyclerView.setAdapter(adapter);
        adapter.SetonitemClickListener(this);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        Log.e("mlist1", "onSuccess: "+mlist.size());
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                list.clear();
                LoadData();
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                // load more data here
//                page++;
                LoadData();
                recyclerView.loadMoreComplete();
            }
        });
    }

    private void LoadData() {
        RequestParams mparams = new RequestParams("http://app.u17.com/v3/appV3_3/android/phone/list/commonComicList?argValue=23&argName=sort&argCon=0&page=1&android_id=4058040115108878&v=3330110&model=GT-P5210&come_from=Tg002");
        x.http().get(mparams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                Data Data = gson.fromJson(result, Data.class);
                list.add(Data.getData().getReturnData());
                for (Data.DataBean.ReturnDataBean a:list) {
                    mlist.addAll(a.getComics());
                }
                Log.e("mlist", "onSuccess: "+mlist.size());

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public void onItemClick(View var2, int var3) {
        Toast.makeText(MainActivity.this,""+var3,Toast.LENGTH_SHORT).show();
    }
}
