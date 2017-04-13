package com.example.yuyu11.duliday.http;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.duliday.business_steering.http.base.MyCallback;
import com.duliday.business_steering.http.base.MyRequst;
import com.duliday.business_steering.http.base.MyResponse;
import com.duliday.business_steering.interfaces.http.Http2Interface;
import com.duliday.business_steering.mode.PartTimeView.PartTimeViewSelectBean;
import com.duliday.business_steering.mode.base.IdBean;
import com.duliday.business_steering.mode.base.JobIdBean;
import com.duliday.business_steering.mode.base.PageBean;
import com.duliday.business_steering.mode.manage.JobRqBean;
import com.duliday.business_steering.mode.news2_0.NewJobIdBean;
import com.duliday.business_steering.mode.news2_0.SendKfMsgBean;
import com.duliday.business_steering.mode.news2_0.SendMsgRqBean;
import com.duliday.business_steering.mode.request.account.AddStoreBean;
import com.duliday.business_steering.mode.request.account.StatusStoreBean;
import com.duliday.business_steering.mode.request.city.CityBean;
import com.duliday.business_steering.mode.request.mation.AuthBean;
import com.duliday.business_steering.mode.request.mation.UpMation;
import com.duliday.business_steering.mode.request.resume.ExportResume;
import com.duliday.business_steering.mode.request.resume.GroupMessageBean;
import com.duliday.business_steering.mode.request.resume.HandleResume;
import com.duliday.business_steering.mode.request.resume.ResumeStatusBean;
import com.duliday.business_steering.mode.request.resume.Resume_Id;
import com.duliday.business_steering.mode.request.sign.Phone;
import com.duliday.business_steering.mode.request.sign.SignUp;
import com.duliday.business_steering.mode.request.sign.Update;
import com.duliday.business_steering.tools.Const;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;

/**
 * Created by yujinzhao on 17/2/20.
 */

public class Http2request {
    private static String TAG = "Http2Request";
    public Context context;

    public Http2request(Context context) {
        this.context = context;
    }


    public <T> void loadDataPost(final String url, final HashMap<String, String> map, final Http2Interface httpInterface) {
        Log.d(TAG, "doPost()=》url:" + url + ",\n请求参数:" + map.toString());
        new MyRequst("POST", url).writeForm(map).fire(context, new MyCallback() {
            @Override
            public void onSuccess(MyResponse response) throws Exception {
                Log.d(TAG, "doPostJson()=》\nresponse!====>" + response.getString());
                httpInterface.ok(response.getString());
            }

            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure");
                httpInterface.error(-1, "");

            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                httpInterface.error(-1, "网络连接失败，请检查网络!!!");
                Log.e(TAG, "onError");
            }

            @Override
            public void onFail(MyResponse response) throws Exception {
                super.onFail(response);
                Log.e(TAG, "onFail");
                httpInterface.error(response.getStatusCode(), response.getString());
                Log.e(TAG, "错误码:" + response.getStatusCode() + "\n错误信息:" + response.getString());
            }
        });
    }

    public <T> void doPostJson(final Object o, final String url, final Http2Interface httpInterface) {
        Log.d(TAG, "doPostJson()=》url:" + url + ",\n请求参数:" + JSON.toJSONString(o));

        new MyRequst("POST", url).writeJson(o).fire(context, new MyCallback() {
            @Override
            public void onSuccess(MyResponse response) throws Exception {
                Log.d(TAG, "doPostJson()=》\nresponse!====>" + response.getString());
                httpInterface.ok(response.getString());

            }

            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure");
                httpInterface.error(-1, "");

            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                httpInterface.error(-1, "网络连接失败，请检查网络!!!");
                Log.e(TAG, "onError");
            }

            @Override
            public void onFail(MyResponse response) throws Exception {
                super.onFail(response);
                Log.e(TAG, "onFail");
                httpInterface.error(response.getStatusCode(), response.getString());
                Log.e(TAG, "错误码:" + response.getStatusCode() + "\n错误信息:" + response.getString());
            }
        });
    }

    public <T> void loadDataGet(String url, final Http2Interface httpInterface) {
        Log.d(TAG, "doGet()=》url:" + url);
        new MyRequst("GET", url).fire(context, new MyCallback() {
            @Override
            public void onSuccess(MyResponse response) throws Exception {
                Log.d(TAG, "response!====>" + response.getStatusCode() + response.getString());
                httpInterface.ok(response.getString());

            }

            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure");
                httpInterface.error(-1, "");

            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                httpInterface.error(-1, "网络连接失败，请检查网络!!!");
                Log.e(TAG, "onError");
            }

            @Override
            public void onFail(MyResponse response) throws Exception {
                super.onFail(response);
                Log.e(TAG, "onFail");
//                Log.e(TAG,"错误码："+response.getStatusCode());
//                Log.e(TAG, "错误码:" + response.getStatusCode() + "\n错误信息:" + response.getString());
                httpInterface.error(response.getStatusCode(), response.getString());


            }
        });
    }

    /**
     * 元信息
     *
     * @param inte
     */
    public void getMeta(Http2Interface inte) {
        loadDataGet(Const.BASE_URL + "/api/common/meta", inte);

    }

    /**
     * 城市列表
     *
     * @param inte
     */
    public void getCities(Http2Interface inte) {
        CityBean cityBean=new CityBean();
        cityBean.setProvince_id(null);
        doPostJson(cityBean, Const.BASE_URL + "/api/common/cities", inte);
//        loadDataGet(Const.BASE_URL + "/api/common/cities", inte);
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param inte
     */
    public void getCode(Phone phone, Http2Interface inte) {
        doPostJson(phone, Const.BASE_URL + "/api/auth/verification-code", inte);
    }

    /**
     * 创建账户
     *
     * @param signUp
     * @param inte
     */
    public void signUp(SignUp signUp, Http2Interface inte) {
        doPostJson(signUp, Const.BASE_URL + "/api/auth/sign-up", inte);
    }

    /**
     * 获取授权
     *
     * @param signup
     * @param inte
     */
    public void userLogin(SignUp signup, Http2Interface inte) {
        doPostJson(signup, Const.BASE_URL + "/api/auth", inte);
    }

    /**
     * 我的认证
     *
     * @param inte
     */
    public void authInfo(Http2Interface inte) {
        loadDataGet(Const.BASE_URL + "/api/auth/info", inte);
    }

    /**
     * 修改信息
     *
     * @param update
     * @param inte
     */
    public void userUpdate(Update update, Http2Interface inte) {
        doPostJson(update, Const.BASE_URL + "/api/auth/info/update", inte);
    }


    /**
     * 更新企业资料
     *
     * @param upMation
     * @param inte
     */
    public void updateMation(UpMation upMation, Http2Interface inte) {
        doPostJson(upMation, Const.BASE_URL + "/api/b/profile/update", inte);
    }

    /**
     * 发布工作
     *
     * @param bean
     * @param http2Interface
     */
    public void jobCreate(PartTimeViewSelectBean bean, Http2Interface http2Interface) {
        doPostJson(bean, Const.BASE_URL + "/api/b/job/create", http2Interface);
    }

    /**
     * 获取企业资料
     *
     * @param idBean
     * @param inte
     */
    public void loadMation(IdBean idBean, Http2Interface inte) {
        doPostJson(idBean, Const.BASE_URL + "/api/b/profile", inte);
    }

    /**
     * 工作的简历
     *
     * @param statusBean
     * @param inte
     */
    public void loadjobResume(ResumeStatusBean statusBean, Http2Interface inte) {
        doPostJson(statusBean, Const.BASE_URL + "/api/b/job/resumes", inte);
    }


    /**
     * 获取工作列表
     *
     * @param jobRqBean
     * @param http2Interface
     */
    public void getJobs(JobRqBean jobRqBean, Http2Interface http2Interface) {
        doPostJson(jobRqBean, Const.BASE_URL + "/api/b/jobs/all", http2Interface);
    }

    /**
     * 地址历史记录
     *
     * @param pageBean
     * @param inte
     */
    public void loadRecord(PageBean pageBean, Http2Interface inte) {
        doPostJson(pageBean, Const.BASE_URL + "/api/b/address/logs", inte);
    }

    /**
     * 工作信息
     *
     * @param job
     * @param inte
     */
    public void getJobdetails(IdBean job, Http2Interface inte) {
        doPostJson(job, Const.BASE_URL + "/api/c/job", inte);
    }

    /**
     * 处理简历
     *
     * @param handleResume
     * @param inte
     */
    public void handleResume(HandleResume handleResume, Http2Interface inte) {
        doPostJson(handleResume, Const.BASE_URL + "/api/b/sign-up/handle/ids", inte);
    }

    /**
     * 工作id查询该工作的联系方式
     *
     * @param id
     * @param http2Interface
     */
    public void jobContacts(int id, Http2Interface http2Interface) {
        JobIdBean jobIdBean = new JobIdBean();
        jobIdBean.setJob_id(id);
        doPostJson(jobIdBean, Const.BASE_URL + "/api/c/job/contacts", http2Interface);
    }

    /**
     * 获取工作的工作地址
     *
     * @param id
     * @param http2Interface
     */
    public void jobAddress(int id, Http2Interface http2Interface) {
        JobIdBean jobIdBean = new JobIdBean();
        jobIdBean.setJob_id(id);
        doPostJson(jobIdBean, Const.BASE_URL + "/api/b/job/addresses", http2Interface);
    }

    /**
     * 门店列表
     *
     * @param page
     * @param http2Interface
     */
    public void stores(int page, Http2Interface http2Interface) {

        Object o = JSON.parse("{ \"page\": " + page + "," +
                "    \"status_ids\": 4}");
        doPostJson(o, Const.BASE_URL + "/api/b/stores", http2Interface);
    }

    /**
     * 生成工作URL
     * @param jobIdBean
     * @param inte
     */
    public void getQRcode(JobIdBean jobIdBean, Http2Interface inte) {
        doPostJson(jobIdBean, Const.BASE_URL + "/api/common/job/url", inte);
    }

    /**
     * 邀请门店
     *
     * @param addStoreBean
     * @param inte
     */
    public void addStore(AddStoreBean addStoreBean, Http2Interface inte) {
        doPostJson(addStoreBean, Const.BASE_URL + "/api/b/store/member/add", inte);
    }

    /**
     * 门店成员
     *
     * @param storeBean
     * @param inte
     */
    public void loadStore(StatusStoreBean storeBean, Http2Interface inte) {
        doPostJson(storeBean, Const.BASE_URL + "/api/b/store/members", inte);
    }

    /**
     * 删除 取消发布 工作
     *
     * @param jobId
     * @param http2Interface
     */
    public void jobDelete(int jobId, Http2Interface http2Interface) {
        JobIdBean jobIdBean = new JobIdBean();
        jobIdBean.setJob_id(jobId);
        doPostJson(jobIdBean, Const.BASE_URL + "/api/b/job/delete", http2Interface);
    }

    /**
     * 工作下架
     *
     * @param jobId
     * @param http2Interface
     */
    public void jobOut(int jobId, Http2Interface http2Interface) {
        JobIdBean jobIdBean = new JobIdBean();
        jobIdBean.setJob_id(jobId);
        doPostJson(jobIdBean, Const.BASE_URL + "/api/b/job/out", http2Interface);
    }

    /**
     * 工作刷新
     *
     * @param jobId
     * @param http2Interface
     */
    public void jobRefresh(int jobId, Http2Interface http2Interface) {
        JobIdBean jobIdBean = new JobIdBean();
        jobIdBean.setJob_id(jobId);
        doPostJson(jobIdBean, Const.BASE_URL + "/api/b/job/refresh", http2Interface);
    }

    /**
     * 工作刷新
     *
     * @param jobId
     * @param http2Interface
     */
    public void jobStick(int jobId, Http2Interface http2Interface) {
        JobIdBean jobIdBean = new JobIdBean();
        jobIdBean.setJob_id(jobId);
        doPostJson(jobIdBean, Const.BASE_URL + "/api/b/job/stick", http2Interface);
    }

    /**
     * 获取评论列表
     *
     * @param jobIdBean
     * @param http2Interface
     */
    public void jobComments(NewJobIdBean jobIdBean, Http2Interface http2Interface){
        doPostJson(jobIdBean,Const.BASE_URL+"/api/b/job/comments",http2Interface);
    }

    /**
     * 发送msg
     *
     * @param bean
     * @param http2Interface
     */
    public void sendMsg(SendMsgRqBean bean, Http2Interface http2Interface) {
        doPostJson(bean, Const.BASE_URL + "/api/b/job/comment/create", http2Interface);
    }

    /**
     * 简历导出
     *
     * @param exportResume
     * @param inte
     */
    public void exportResume(ExportResume exportResume, Http2Interface inte) {
        doPostJson(exportResume, Const.BASE_URL + "/api/b/sign-up/export/ids", inte);
    }

    /**
     * 获取简历信息
     *
     * @param resume_id
     * @param inte
     */
    public void getResume(Resume_Id resume_id, Http2Interface inte) {
        doPostJson(resume_id, Const.BASE_URL + "/api/c/resume", inte);
    }

    /**
     * 评论工作列表
     *
     * @param pageBean
     * @param inte
     */
    public void loadloadcomment(PageBean pageBean, Http2Interface inte) {
        doPostJson(pageBean, Const.BASE_URL + "/api/b/commented/jobs", inte);
    }

    /**
     * 群发通知
     *
     * @param messageBean
     * @param inte
     */
    public void groupMessage(GroupMessageBean messageBean, Http2Interface inte) {
        doPostJson(messageBean, Const.BASE_URL + "/api/b/sign-up/message/ids", inte);
    }

    /**
     * 提交认证
     *
     * @param authBean
     * @param inte
     */
    public void updataAuth(AuthBean authBean, Http2Interface inte) {
        doPostJson(authBean, Const.BASE_URL + "/api/b/profile/verification/update", inte);
    }

    /**
     * 工作地址列表
     *
     * @param jobAddresses
     * @param inte
     */
    public void loadJobsAddresses(JobIdBean jobAddresses, Http2Interface inte) {
        doPostJson(jobAddresses, Const.BASE_URL + "/api/b/job/addresses", inte);
    }

    /**
     * 获取  客服消息
     * @param pageBean
     * @param http2Interface
     */
    public void loadMessages(PageBean pageBean ,Http2Interface http2Interface){
        doPostJson(pageBean, Const.BASE_URL + "/api/b/messages", http2Interface);
    }

    /**
     * 发送消息到客服
     * @param bean
     * @param http2Interface
     */
    public void sendKfMessage(SendKfMsgBean bean, Http2Interface http2Interface){
        doPostJson(bean, Const.BASE_URL + "/api/b/message/create", http2Interface);
    }

    /**
     * 检测商家信息是否完善
     * @param http2Interface
     */
    public void available(Http2Interface http2Interface){
        loadDataGet(Const.BASE_URL + "/api/b/job/create/available",http2Interface);
    }
}
