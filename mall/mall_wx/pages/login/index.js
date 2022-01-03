import Toast from "../../libs/dist/toast/toast";

// pages/login/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    phone: '',
    password: ''
  },

  doLogin: function (e) {
    var formData = e.detail.value;
    if (formData.phone == '' || formData.password == '') {
      Toast.fail('手机号或密码未输入！');
      return;
    }
    wx.request({
      url: 'http://localhost:8080/mall/login',
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      data: formData,
      success: function (res) {
        if (res.data === "error") {
          Toast.fail('手机号未注册或密码错误！');
          return;
        }
        Toast.success('登录成功，欢迎使用！');
        wx.setStorageSync("c_id", res.data); // 存入本地缓存
        setTimeout(function () {
          wx.switchTab({
            url: '/pages/goods_list/index',
          })
        }, 1000); // 延迟1秒再跳转
      },
      fail: function () {
        Toast.fail('无法连接至服务器，请重试！');
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    // 隐藏登录界面左上角的Home
    wx.hideHomeButton({
      success: (res) => {},
    })
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

  },

})