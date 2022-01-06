// pages/logup/index.js
import Toast from "../../libs/dist/toast/toast";
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    name: "",
    account: "",
    type: "",
    fits: [
      'cover'
    ],
    gender: '',
    password: '',
    password_confirm: '',
    addr: '',
    src: ""
  },

  // 选择性别
  onChange(event) {
    console.log("#####",event);
    this.setData({
      radio: event.detail,
      src: event.detail == '1' ? app.maleImage : app.femaleImage
    });
  },

  // 验证注册表单是否已填写完成
  checkFormComplete: function (e) {
    var formData = e.detail.value;
    for (var item in formData) {
      if (item == "password" || item == "password_confirm") continue;
      if (formData[item] == '')
        return false;
    }
    return true;
  },

  // 修改
  changeInfo: function (e) {
    var that = this;
    var formData = e.detail.value;
    if (formData.password != formData.password_confirm) {
      Toast.fail('两次输入的密码不一致！');
    } else if (!that.checkFormComplete(e)) {
      Toast.fail('请确认填写完成所有项！');
    } else {
      wx.request({
        url: app.enabledUrl + '/mall/changeInfo',
        method: 'POST',
        header: {
          'content-type': 'application/json'
        },
        data: formData,
        success: function (res) {
          if (res.data === "resetPsw") {
            Toast.success('密码修改成功！请重新登陆！');
            setTimeout(function () {
              wx.redirectTo({
                url: '/pages/login/index'
              });
            }, 1000); // 延迟1秒再跳转
          } else if (res.data === "pswNotChange") {
            Toast.success('信息修改成功！');
            setTimeout(function () {
              wx.switchTab({
                url: '/pages/user/index'
              });
            }, 500); // 延迟0.5秒再跳转
          } else {
            Toast.fail('修改失败！');
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
    var that = this;
    var c_id = wx.getStorageSync('c_id');
    wx.request({
      url: app.enabledUrl + '/mall/getCustomerInfoByCid?c_id=' + c_id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          name: res.data.name,
          account: res.data.phone,
          addr: res.data.address,
          gender: res.data.gender == 'male' ? '1' : '0',
          src: res.data.gender == 'male' ? app.maleImage : app.femaleImage
        })
      }
    });
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