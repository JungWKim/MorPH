package com.example.myapplication.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.presenter.symbolic_constants.Permisson;
import com.example.myapplication.model.dto.MemoDTO;
import com.example.myapplication.presenter.Presenter;
import com.example.myapplication.util.MemoAdapter;
import com.example.myapplication.R;
import com.example.myapplication.presenter.symbolic_constants.Mode;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar toolbar;
    private FloatingActionButton faBtnExtend, faBtnReduce, faBtnReadData, faBtnWrite;
    private Button btnSetting;
    private TextView tvContents,tvTitle;
    private View main_Click;
    private NavigationView navigationView;
    private ListView listView;
    //private String path;
    String txt_title, txt_contents, main_title, main_contents;
    private boolean isFabOpen = false;
    private Animation fab_open, fab_close;
    // 메모 쿼리 수행
    //private MemoDAO mMemoDAO;
    private MemoAdapter mMemoAdapter;
    private int mCurrentLongClickPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLoadingActivty();//로딩화면 출력
        Presenter.registerContext(getApplicationContext());
        Presenter.showCheckPermission(MainActivity.this);

        //onCreate에서 로직을 제거하고 메소드만 호출하는 걸로 실행 절차를 육안으로 확인.
        linkViews();//위에 레퍼런스들에 id를 link함.
        settingActionBar();
        settingDrawerLayout();
        settingNavigationView();
        settingListView();
        registerEvent();
    }

    public void showLoadingActivty() {
        Intent loadingintent = new Intent(this, LoadingActivity.class);
        startActivity(loadingintent);
    }
    public void settingActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //액션바에 제목 없앰
        getSupportActionBar().setElevation(-1); //액션바 그림자 삭제
    }
    public void settingDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    public void settingNavigationView() {
        NaviEvent naviEvent = new NaviEvent();
        navigationView.setNavigationItemSelectedListener(naviEvent.naviEvent);
        SharedPreferences sf = getSharedPreferences("favorite",MODE_PRIVATE);
        tvTitle .setText(sf.getString("title_favor","즐겨찾기 제목"));
        tvContents.setText(sf.getString("contents_favor","아래 카드를 길게 탭하여 즐겨찾기를 추가하세요!"));
    }
    private void settingListView() {
        listView.setAdapter(mMemoAdapter=Presenter.getMemoAdapter());
        ListViewEvent listViewEvent = new ListViewEvent();
        listView.setOnItemClickListener(listViewEvent.lvOnClick);
        listView.setOnItemLongClickListener(listViewEvent.lvOnLongClick);
        registerForContextMenu(listView);
    }
    public void linkViews() {
        mContext = getApplicationContext();
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        //mMemoDAO = new MemoDAO(getApplicationContext());

        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);

        faBtnExtend = findViewById(R.id.faBtnExtend);
        faBtnReduce = findViewById(R.id.faBtnReduce);
        faBtnReadData = findViewById(R.id.faBtnReadData);
        faBtnWrite = findViewById(R.id.faBtnWrite);

        listView = findViewById(R.id.lv);

        tvTitle = findViewById(R.id.tvTitle);
        tvContents = findViewById(R.id.tvContents);
        main_Click = findViewById(R.id.main_click);//main_click -> content_main.xml의 linearlayout
        btnSetting = findViewById(R.id.btnSetting);
    }
    public void registerEvent() {
        BtnEvent btnEvent = new BtnEvent();
        btnSetting.setOnClickListener(btnEvent.btnSettingEvent);
        faBtnExtend.setOnClickListener(btnEvent.faBtnExtendEvent);
        //faBtnReduce.setOnClickListener(btnEvent.);
        faBtnReadData.setOnClickListener(btnEvent.faBtnReadDataEvent);
        faBtnWrite.setOnClickListener(btnEvent.faBtnWriteEvent);
        main_Click.setOnClickListener(btnEvent.btnMainEvent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { super.onBackPressed(); }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void toggleFab() {
        if (isFabOpen) {
            faBtnExtend.setImageResource(R.drawable.plus_bt);
            faBtnReadData.startAnimation(fab_close);
            faBtnWrite.startAnimation(fab_close);
            faBtnReadData.setClickable(false);
            faBtnWrite.setClickable(false);
            isFabOpen = false;
        }
        else {
            faBtnExtend.setImageResource(R.drawable.close_bt);
            faBtnReadData.startAnimation(fab_open);
            faBtnWrite.startAnimation(fab_open);
            faBtnReadData.setClickable(true);
            faBtnWrite.setClickable(true);
            isFabOpen = true;
        }
    }
    // ListView Item 롱클릭시 보여줄 ContextMenu 세팅

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.memo_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    // ContextMenu 클릭시 수정 또는 삭제 작업 수행
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        MemoDTO selectedItem = (MemoDTO) mMemoAdapter.getItem(mCurrentLongClickPosition);

        switch (item.getItemId()) {
            // db 에서 해당 메모 삭제 후 화면 갱신
            case R.id.menu_delete:
                Presenter.delete(selectedItem.getId());
                Presenter.swapData(mMemoAdapter);
                break;
            // 수정을 위해 ViewerActivity 실행
            case R.id.menu_modify:
                Intent modifyIntent = new Intent(getApplicationContext(), ViewerActivity.class);
                Presenter.setCurrentMode(Mode.UPDATE);
                Presenter.setCurrentMemoDTO(selectedItem);
                startActivityForResult(modifyIntent, Permisson.REQUEST_WRITE);
                break;
            case R.id.menu_favorite:
                tvTitle.setText(selectedItem.getTitle());
                tvContents.setText(selectedItem.getContents());
                main_title = selectedItem.getTitle();
                main_contents = selectedItem.getContents();
                break;
        }
        return true;
    }

    /* 수정 또는 메모 추가 모드로 ViewerActivity 실행 후 종료시 호출 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK && data == null) return;
        MemoDTO memo = (MemoDTO) data.getSerializableExtra("memo");
        int mode = data.getIntExtra("mode", -1);
        Presenter.dataSave(requestCode,mode,memo);
        Presenter.swapData(mMemoAdapter);
    }

    private void onTextRead() {
        //텍스트파일을 저장소에서 가져온다
        Toast.makeText(this, "morPH폴더의 txt파일을 출력합니다.", Toast.LENGTH_SHORT).show();
        Presenter.enableTextReader();
        new AlertDialog.Builder(this)
                .setTitle("morPH")
                .setItems(Presenter.getFileNamesForReading(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), ViewerActivity.class);
                        // TODO Auto-generated method stub
                        MemoDTO temp = Presenter.readData(arg1);
                        Presenter.setCurrentMode(Mode.INSERT);
                        Presenter.setCurrentMemoDTO(temp);
                        startActivityForResult(intent,Permisson.REQUEST_WRITE);//노트 추가
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        }).show();

    }
    private void onTextWriting(String title,String body){
        Presenter.enableTextWriter();
        if(Presenter.writeData(title,body)) Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
    } //텍스트파일 저장소에 저장한다.
    private void deltext(){
        Presenter.enableTextDeleter();
        new AlertDialog.Builder(this)
                .setTitle("TEXT FILE LIST")
                .setItems(Presenter.getFileNamesForDeleting(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Presenter.deleteData(arg1);
                        deltext();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        }).show();
    } //텍스트파일을 저장소에서 삭제한다
    //.txt파일만을 보여주도록 한다.

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(Presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)) showToast_PermissionDeny();
    }

    private void showToast_PermissionDeny() {
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다. 설정에서 권한 허용 하시기 바랍니다.", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("favorite", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("title_favor", main_title);
        editor.putString("contents_favor", main_contents);
        editor.commit();
    }

    //뷰 이벤트 클래스로 정의
    private class BtnEvent {
        View.OnClickListener faBtnWriteEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메모 추가 버튼 클릭시 호출 - 메모 추가 모드
                Intent addNewMemoIntent = new Intent(getApplicationContext(), ViewerActivity.class);
                Presenter.setCurrentMode(Mode.INSERT);
                Presenter.setCurrentMemoDTO(new MemoDTO());
                startActivityForResult(addNewMemoIntent, Permisson.REQUEST_WRITE);
                toggleFab();
            }
        };
        View.OnClickListener faBtnExtendEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        };
        View.OnClickListener faBtnReadDataEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTextRead();//여기서도 intent 사용하므로 onClick 밑에 두었다.
                toggleFab();
            }
        };
        View.OnClickListener btnSettingEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SettingActivity.class);//환경설정으로 이동하는 Intent i
                startActivity(i);
            }
        };
        View.OnClickListener btnMainEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(main_title != null){
                    Intent intent = new Intent(getApplicationContext(), ViewerActivity.class);
                    Presenter.setCurrentMode(Mode.INSERT);
                    Presenter.setCurrentMemoDTO(new MemoDTO(main_title,main_contents));
                    startActivityForResult(intent, Permisson.REQUEST_WRITE);
                }
            }
        };
    }
    private class ListViewEvent {
        AdapterView.OnItemClickListener lvOnClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                MemoDTO item = (MemoDTO) mMemoAdapter.getItem(position);
                Intent memoViewerIntent = new Intent(getApplicationContext(), ViewerActivity.class);
                Presenter.setCurrentMode(Mode.READ);
                Presenter.setCurrentMemoDTO(item);
                startActivity(memoViewerIntent);
            }
        };
        AdapterView.OnItemLongClickListener lvOnLongClick = new AdapterView.OnItemLongClickListener() {
            /* ListView Item 롱클릭시 호출
               - 메모 수정모드
               - 메모 삭제모드 */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentLongClickPosition = position;
                return false;
            }
        };
    }
    private class NaviEvent {
        NavigationView.OnNavigationItemSelectedListener naviEvent = new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }
}