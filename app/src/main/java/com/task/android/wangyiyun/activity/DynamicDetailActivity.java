package com.task.android.wangyiyun.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.task.android.wangyiyun.R;
import com.task.android.wangyiyun.bean.CommentInfo;
import com.task.android.wangyiyun.bean.UserInfo;
import com.task.android.wangyiyun.dialog.CommentDialog;
import com.task.android.wangyiyun.fragement.FriendCircleFragement;
import com.task.android.wangyiyun.util.DensityUtil;
import com.task.android.wangyiyun.util.KeyboardChangeListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

public class DynamicDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DynamicDetailActivity";
    private ImageView iv_comment;
    private Toolbar toolBar;
    private LinearLayout ll_back;
    private List<UserInfo> userInfoList;
    private LinearLayout ll_praiseList;
    private boolean hasPraise = true;
    private RecyclerView mrecyclerView;
    private CommonAdapter<CommentInfo> madapter;
    private List<CommentInfo> mInfoList = new ArrayList<>();
    private HeaderAndFooterWrapper mheaderAdapter;
    private View comment_top;
    private View praiseList;
    private View noPraise;
    private EditText et_dynamic_detail_footer;
    private RelativeLayout rl_dynamicdetail_praise_share;
    private TextView tv_send;
    private LinearLayout ll_dynamicdetail_footer;
    private ImageView iv_footer_praise;
    private int dynamicID;
    private int dynamicPraiseNumber;
    private boolean userHasPraise;
    private TextView tv_praiseNumber;
    private final int COMMENT_REPLY = 1;  // 代表回复Comment
    private final int COMMENT_COMMENT = 0; //  代表评论Dynamic
    private CommentInfo mInfo = null;  // 输入框代表的用户信息，将要进入conmment列表
    private String beReplyerName = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_detail);
        init();
    }

    private void init() {
        mrecyclerView = (RecyclerView) findViewById(R.id.dynamic_detail_rv);  // 评论列表
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 把评论的具体内容，赞列表和未赞时的视图添加到recyclerView头部
        comment_top = LayoutInflater.from(this).inflate(R.layout.dynamic_detail_top, mrecyclerView, false);
        praiseList = LayoutInflater.from(this).inflate(R.layout.dynamicdetail_praiselist, mrecyclerView, false);
        noPraise = LayoutInflater.from(this).inflate(R.layout.dynamic_detail_nopraise, mrecyclerView, false);
        iv_comment = (ImageView) comment_top.findViewById(R.id.dynamicDetail_comment); //评论中的图片
        toolBar = (Toolbar) findViewById(R.id.toolbar_dynamicdetail);  // toolBar
        ll_back = (LinearLayout) findViewById(R.id.dynamicdetail_back); // 返回键
        et_dynamic_detail_footer = (EditText) findViewById(R.id.dynamic_detail_et_footer);// 输入框
        rl_dynamicdetail_praise_share = (RelativeLayout) findViewById(R.id.dynamicdetail_rl_praise_share);// 输入框旁的分享和赞图标
        tv_send = (TextView) findViewById(R.id.tv_dynamicdetail_send); // 输入框旁的发送按钮
        ll_dynamicdetail_footer = (LinearLayout) findViewById(R.id.dynamic_detail_footer);// 输入框所在父view
        iv_footer_praise = (ImageView) findViewById(R.id.dynamicdetail_footer_praise);// 评论框所在的赞图标
        tv_praiseNumber = (TextView) findViewById(R.id.dynamicdetail_footer_praiseNumber);
        mInfo = new CommentInfo();

        tv_send.setVisibility(View.GONE);

        if (FriendCircleFragement.userID!=null){
            initEditText();  // 初始化评论框
        }else{
            et_dynamic_detail_footer.setInputType(InputType.TYPE_NULL);
            et_dynamic_detail_footer.setFocusable(false);

        }
        Intent intent = getIntent();
        dynamicID = intent.getIntExtra("dynamicInfo", -1); // 获得dynamic的ID
        dynamicPraiseNumber = intent.getIntExtra("dynamicPraiseNumber",0); // 获得dynamic的赞数量
        userHasPraise = intent.getBooleanExtra("dynamicHasPraise",false);  // 获得是否已赞

        et_dynamic_detail_footer.setBackgroundResource(R.drawable.dynamicdetail_et_footer);
        et_dynamic_detail_footer.setOnClickListener(this);
        tv_send.setOnClickListener(this);
        iv_footer_praise.setOnClickListener(this);
//        tv_praiseNumber.setText(dynamicPraiseNumber);

        if (userHasPraise){
            iv_footer_praise.setImageResource(R.drawable.zan_red_selected);
        }else{
            iv_footer_praise.setImageResource(R.drawable.zan_gray_unselected);
        }


        initPraiseList();   // 初始化评论列表

        setSupportActionBar(toolBar);  // 设置toolBar
        Glide.with(this).load(R.drawable.scene).into(iv_comment);
        ll_back.setOnClickListener(this);

        initUserInfoList();  // 初始化赞列表的用户信息

        mrecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        initCommnetInfo(); // 初始化评论列表的用户信息

        madapter = new CommonAdapter<CommentInfo>(this, R.layout.rv_item_commentlist, mInfoList) {
            @Override
            protected void convert(ViewHolder holder, CommentInfo commentInfo, int position) {
                holder.setText(R.id.commentlist_tv_name,commentInfo.getCommenterName());
                int commenterAvatar = getResource(commentInfo.getCommenterAvatar());
                holder.setImageResource(R.id.commentlist_userAvatar, commenterAvatar);
                holder.setText(R.id.commenlist_tv_comment, commentInfo.getComment());

            }
        };
        mheaderAdapter = new HeaderAndFooterWrapper(madapter);
        mheaderAdapter.addHeaderView(comment_top);

        hasPraise = hasPraise(); // 判断此dynamic是否已被赞
        if (hasPraise) {
            mheaderAdapter.addHeaderView(praiseList); // 如果已被赞，则加载praiseList
        } else {
            mheaderAdapter.addHeaderView(noPraise); // 如果没被赞，则加载noPraise
        }

        mrecyclerView.setAdapter(mheaderAdapter);
        mheaderAdapter.notifyDataSetChanged();

        madapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                position = position - mheaderAdapter.getHeadersCount();
                Log.i(TAG, " count = " + madapter.getItemCount() + "  position = " + position +
                        "  po = " + position);
                switch (view.getId()){
                    case R.id.rv_item_commentlist:
                        if (FriendCircleFragement.userID == null){
                            IntentToLogin();
                        }else {
                            // 设置minfo的信息，以便评论或者回复时使用
                            mInfo.setID(FriendCircleFragement.userID);
                            mInfo.setReplyerID(mInfoList.get(position).getID());
                            mInfo.setRelay(mInfoList.get(position).getComment());
                            mInfo.setCommenterName(mInfoList.get(position).getCommenterName());
                            Log.i(TAG,"  position = " + position + "  commenterName = " + mInfoList.get(position).getCommenterName());
                            // 创建Dialog
                            final CommentDialog commentDialog = new CommentDialog(DynamicDetailActivity.this);
                            // 给dialog传入自定义布局
                            commentDialog.setContentView(R.layout.comment_choose);
                            commentDialog.show();  // 显示dialog
                            //  获取Dialog的子控件
                            LinearLayout ll_comment_reply = (LinearLayout) commentDialog.findViewById(R.id.ll_comment_reply);
                            // 设置子控件的点击事件
                            ll_comment_reply.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    commentDialog.dismiss();
                                    // 需要延时处理，使得editText得到重绘，以便获取焦点，弹出软键盘
                                    getWindow().getDecorView().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            if (imm != null) {
                                                et_dynamic_detail_footer.setFocusable(true);
                                                et_dynamic_detail_footer.setFocusableInTouchMode(true);
                                                et_dynamic_detail_footer.requestFocus();
                                                imm.showSoftInput(et_dynamic_detail_footer, 0);
                                            }
                                        }
                                    }, 100);

                                }
                            });
                        }

                        break;
                    default:
                        break;

                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void initEditText() {

        et_dynamic_detail_footer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable ==null || editable.toString().equals("")){
                    // 当评论为空时，发送按钮为灰色
                    tv_send.setTextColor(Color.parseColor("#cdcdcd"));

                }else{
                    // 当评论为空时，发送按钮为黑色
                    tv_send.setTextColor(Color.parseColor("#515151"));
                }
            }
        });

        // 监听键盘的展开与收起
        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) { // isShow判断键盘是否展开
                int rate = DensityUtil.dip2px(DynamicDetailActivity.this,1);  // 把px转换成dp
                initsmileface(isShow);    // 笑脸的展开与收起
                if (isShow){
                    // 如果为回复信息，把输入框的提示文字变成被回复者的昵称
                    if (mInfo.getID() == FriendCircleFragement.userID){
                        if (mInfo.getCommenterName() !=null){
                            String edit_Hint = "✎回复"+mInfo.getCommenterName();
                            et_dynamic_detail_footer.setHint(edit_Hint);
                        }

                    }

                    rl_dynamicdetail_praise_share.setVisibility(View.GONE);  //隐藏赞与分享按钮
                    tv_send.setVisibility(View.VISIBLE);  // 显示发送按钮

                    // 设置评论母控件的模式
                    ll_dynamicdetail_footer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    ll_dynamicdetail_footer.setMinimumHeight(rate *45); // 设置评论母控件的最小值

                } else {

                    ll_dynamicdetail_footer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ll_dynamicdetail_footer.getHeight()));
                    et_dynamic_detail_footer.clearFocus();
                    tv_send.setVisibility(View.GONE);
                    rl_dynamicdetail_praise_share.setVisibility(View.VISIBLE);
//                    ll_dynamicdetail_footer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                }
            }
        });
    }

    private void initsmileface(boolean isShow) {
        // 根据键盘的展开或收起来显示或隐藏笑脸
        if (isShow){
            Drawable dr = getResources().getDrawable(R.drawable.smileface_gray, null);
            int rate = DensityUtil.dip2px(this, 1);
            dr.setBounds(0, 0, 25 * rate, 25 * rate);
            et_dynamic_detail_footer.setCompoundDrawables(null, null, dr,null);
        }else{
            et_dynamic_detail_footer.setCompoundDrawables(null,null,null,null);
        }

    }

    private boolean hasPraise() {  // 判断此dynamic是否已被赞
        Intent i = getIntent();
        int dynamicPraiseNumber = i.getIntExtra("dynamicPraiseNumber", 0); // 获得dynamicActivity页面传递过来的关于dynamic的相关信息
        if (dynamicPraiseNumber == 0) {
            hasPraise = false;
        }
        Log.i(TAG, "  " + hasPraise);
        return hasPraise;
    }


    private void initCommnetInfo() {  // 初始化评论列表用户的信息

//        Intent intent = getIntent();
//        final int dynamicID = intent.getIntExtra("dynamicInfo", -1);// 获取此dynamic的ID
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    Connection con = DriverManager.getConnection(DynamicActivity.aliyunSqlUrl, "root", "h1616408");
//
//                    String sql = "select * from comment where DID = ?";  // 获取此Dynamic的评论的用户
//                    PreparedStatement ps = con.prepareStatement(sql);
//                    ps.setString(1, dynamicID + "");
//                    ResultSet res = ps.executeQuery();
//                    int i = 0;
//                    while (res.next()) {
//                        CommentInfo com = new CommentInfo();
//                        com.setCID(res.getInt("CID"));
//                        com.setID(res.getString("commenterID"));
//                        com.setComment(res.getString("comment"));
//                        com.setPraiseNumber(res.getInt("praiseNumber"));
//
//                        sql = "select * from user where ID  = ?"; // 获取commenter的昵称和头像
//                        ps = con.prepareStatement(sql);
//                        ps.setString(1,res.getString("commenterID"));
//                        ResultSet ress = ps.executeQuery();
//                        if (ress.next()){
//                            com.setCommenterName(ress.getString("name"));
//                            com.setCommenterAvatar(ress.getString("userAvatar"));
//                        }
//                        mInfoList.add(com);  // 把评论的用户加入到列表中
//                        Log.i(TAG," mInfolist.size = " + mInfoList.size());
//                    }
//
////                    sql = "select * from user where ID in (select commenterID from comment)";
////                    Statement st = con.createStatement();
////                    res = st.executeQuery(sql);
////                    while (res.next()){
////                        UserInfo user = new UserInfo();
////                        user.setName(res.getString("name"));
////                        user.setUserAvatar(res.getString("userAvatar"));
////                    }
//
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mheaderAdapter.notifyDataSetChanged();  // 更新界面
//                        }
//                    });
//
//                    con.close();
//                    ps.close();
//                    res.close();
//
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (SQLException e) {
//                    Log.i(TAG,"数据库加载失败了");
//                    e.printStackTrace();
//                }
//
//
//            }
//        }).start();
    }

    private void initPraiseList() {
    }

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int rate = DensityUtil.dip2px(DynamicDetailActivity.this, 1);
            switch (msg.what) {
                case 1:
                    if (null != userInfoList && null != ll_praiseList) {
                        UserInfo user = (UserInfo) msg.obj;

                        ImageView iv = new ImageView(DynamicDetailActivity.this);

//                        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));  //设置图片宽高
//                        iv.setImageResource(user.getUserAvatar()); //图片资源/
                        Log.i("TAG","  " + getResources().getResourceName(R.drawable.wangyiiocn) + "  " + user.getUserAvatar()  );
                        int userAvatarId = getResource(user.getUserAvatar());
                        Glide.with(DynamicDetailActivity.this).load(userAvatarId).into(iv);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(35 * rate, 35 * rate);
                        params.setMargins(0, 0, 3 * rate, 0);
                        iv.setLayoutParams(params);
                        ll_praiseList.addView(iv);

                        Log.i(TAG, "  imageView添加成功" + R.drawable.scene + "  " +
                                DensityUtil.dip2px(DynamicDetailActivity.this, 1));
                    }
                    break;
                case 2:
                    if (null == mInfoList) {
                        mInfoList = new ArrayList<>();
                    }
                    CommentInfo com = (CommentInfo) msg.obj;
                    mInfoList.add(com);
                    mheaderAdapter.notifyDataSetChanged();
                    Log.i("TAG", "  " + "comment " + msg.arg1);

                    break;
                default:
                    break;
            }
        }
    };

    private void initUserInfoList() {
//        if (hasPraise) {
//            // 初始化linearLayout，用linearLayout动态增加赞的用户的头像
//            ll_praiseList = (LinearLayout) praiseList.findViewById(R.id.item_dynamicdetail_ll_praiselist);
//            userInfoList = new ArrayList<>();
//
//            Log.i(TAG, "  开始连接数据库");
//            new Thread(new Runnable() {
//                @Override
//                public void run() {  // 开启线程连接数据库
//                    try {
//                        Class.forName("com.mysql.jdbc.Driver");
//                        Connection con = DriverManager.getConnection(DynamicActivity.aliyunSqlUrl, "root", "h1616408");
//
//                        Log.i(TAG,"数据库加载成功  DynamicId: " + dynamicID);
//                        // 从数据库获取此dynamic的赞的用户
//                        String sql = "select * from user where ID in " +
//                                "(select praiseID  from praise where DID = ?) order by ID ASC";
////                        ResultSet res = st.executeQuery(sql);
//                        PreparedStatement ps = con.prepareStatement(sql);
//
//                        ps.setString(1, dynamicID + "");
//                        ResultSet res = ps.executeQuery();
//                        Log.i(TAG, "  SQl 执行成功   ");
//
//                        int i = 0;
//                        while (res.next() && i<8) { // 赞列表最多放八个用户的头像
//                            final UserInfo user = new UserInfo();
//                            user.setID(res.getString("ID"));
//                            user.setName(res.getString("name"));
//                            user.setUserAvatar(res.getString("userAvatar"));
//                            userInfoList.add(user);
//                            Message message = handle.obtainMessage();
//                            message.obj = user;
//                            message.what = 1;
//                            handle.sendMessage(message);
//                            i++;
//                            Log.i(TAG, "  数据库查询过程中  " + i + "  " + res.getString("ID"));
//
//                        }
//
//                        con.close();
//                        ps.close();
//                        res.close();
//
//                    } catch (SQLException e) {
//                        Log.e(TAG, "数据库连接失败");
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dynamicdetail_back:
                finish();
                break;
            case R.id.dynamic_detail_et_footer:
                if (FriendCircleFragement.userID == null){
                    // 如果当前为游客状态，点击评论时跳转到登录页面
//                    if (FriendCircleFragement.userID == null){
//                        Intent ii = new Intent(this,LoginActivity.class);
//                        startActivity(ii);
//                    }
                }

                break;
            case R.id.dynamicdetail_footer_praise:
            if (FriendCircleFragement.userID == null){
                    IntentToLogin();
            }else{
                if (userHasPraise){  // 如果已赞，赞图标改为未赞
                    iv_footer_praise.setImageResource(R.drawable.zan_gray_unselected);
                    dynamicPraiseNumber--;
                    userHasPraise = false;
                } else {  // 如果未赞，改为已赞
                    iv_footer_praise.setImageResource(R.drawable.zan_red_selected);
                    dynamicPraiseNumber++;
                    userHasPraise= true;
                }
            }
            updatePraise();
                break;
            case R.id.tv_dynamicdetail_send:
                if (!et_dynamic_detail_footer.getText().toString().equals("")){
                    Log.i(TAG," 评论的内容是：" + et_dynamic_detail_footer.getText().toString());
                    sendComment(et_dynamic_detail_footer.getText().toString());

                    // 当点击发送按钮时隐藏软键盘，并将editText清空
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et_dynamic_detail_footer.getWindowToken(), 0);
                    et_dynamic_detail_footer.setText("");

                }
                break;
            default:
                break;
        }
    }

    private void IntentToLogin() {
        // 如果当前为游客状态，点击评论时跳转到登录页面
//            Intent ii = new Intent(this,LoginActivity.class);
//            startActivity(ii);
    }

    private void updatePraise() {
//        final String zanSql = "update dynamic set praiseNumber = " +  dynamicPraiseNumber +
//                " where DID = " + dynamicID;  // 更新赞的数量
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    Connection con = DriverManager.getConnection(DynamicActivity.aliyunSqlUrl, "root", "h1616408");
//
//                    Statement ps = con.createStatement();
//                    ps.execute(zanSql);
//
//                    String updatePraise = null;
//                    String userID = DynamicActivity.userID;
//                    if (!userHasPraise) {  // 如果已赞，取消赞的同时,把该userID从praise表中移除
//                        updatePraise = "delete from praise where praiseID = " + userID +
//                                " and DID = " + dynamicID;
//                        Log.i("DynamicActivity","  " + updatePraise);
//
//                    } else {
//                        updatePraise = "insert into praise values(" + dynamicID + ","
//                                + userID + ")";
//                        Log.i("DynamicActivity","  " + updatePraise);
//                    }
//                    ps.execute(updatePraise);
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private void sendComment(final String com) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Class.forName("com.mysql.jdbc.Driver");
//                    Connection con = DriverManager.getConnection(DynamicActivity.aliyunSqlUrl, "root", "h1616408");
//                    String sql = "insert into comment(DID,CID,commenterID,comment,replyerID,reply,praiseNumber) values" +
//                            "(?,?,?,?,?,?,0)";
//                    PreparedStatement ps = con.prepareStatement(sql);
//                    ps.setInt(1,dynamicID);
//                    int CID = mInfoList.size() + 1;
//                    ps.setInt(2,CID);
//                    String commenterID = DynamicActivity.userID;
//                    ps.setString(3,commenterID);
//
//                    String comment = com;
//                    Log.i(TAG,"Comment:" + com);
//                    ps.setString(4,comment);
//
//                    if (mInfo.getReplyerID() != null){
//                        ps.setString(5,mInfo.getReplyerID());
//                        ps.setString(6,mInfo.getRelay());
//                    } else {
//                        ps.setString(5,null);
//                        ps.setString(6,null);
//                    }
//
//                    ps.execute();
//
//                    mInfo.setDID(dynamicID);
//                    mInfo.setCID(CID);
//                    mInfo.setComment(comment);
//                    mInfo.setID(commenterID);
//                    mInfo.setPraiseNumber(0);
//                    mInfo.setCommenterAvatar(DynamicActivity.userAvatar);
//                    mInfoList.add(mInfo);
//                    mInfo.setCommenterAvatar(DynamicActivity.userAvatar);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mheaderAdapter.notifyDataSetChanged();
//                        }
//                    });
//
//                    ps.close();
//                    con.close();
//
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }).start();

    }

    public int getResource(String imageName) {
        Context ctx = getBaseContext();
        return getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
    }
}