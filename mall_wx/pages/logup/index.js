import Toast from "../../libs/dist/toast/toast";

const app = getApp();

// pages/logup/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: '',
    gender: '',
    phone: '',
    password: '',
    password_confirm: '',
    address: ''
  },

  //选择性别
  onChange(event) {
    this.setData({
      radio: event.detail,
    });
  },

  // 验证注册表单是否已填写完成
  checkFormComplete: function (e) {
    var formData = e.detail.value;
    for (var item in formData) {
      if (formData[item] == '')
        return false;
    }
    return true;
  },

  // 验证手机号是否有效
  isPhoneAvailable: function (phoneInput) {
    var myreg = /^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[0-9])|(18[0-9])|(19[1,8,9]))\d{8}$/;
    if (!myreg.test(phoneInput)) {
      return false;
    } else {
      return true;
    }
  },

  //注册
  doLogup: function (e) {
    var that = this;
    var formData = e.detail.value;
    if (!that.checkFormComplete(e)) {
      Toast.fail('请确认填写完成所有项！');
    } else if (formData.password != formData.password_confirm) {
      Toast.fail('两次输入的密码不一致！');
    } else if (!that.isPhoneAvailable(formData.phone)) {
      Toast.fail('请输入有效的11位手机号码！');
    } else {
      wx.request({
        url: app.enabledUrl + '/mall/logup',
        method: 'POST',
        header: {
          'content-type': 'application/json'
        },
        data: formData,
        success: function (res) {
          if (res.data === "ok") {
            Toast.success('注册成功！');
            setTimeout(function () {
              wx.redirectTo({
                url: '/pages/login/index'
              });
            }, 1000); // 延迟1秒再跳转
          } else {
            Toast.fail('注册失败，此手机号已注册！');
          }
        },
        fail: function () {
          Toast.fail('无法连接至服务器，请重试！');
        }
      })
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})