// pages/history_order/unchecked/index.js

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    active: 0,
    orders: []
  },
  changePage: function (event) {
    var temp = event.detail.name;
    if (temp == "1") {
      wx.redirectTo({
        url: '../../history_order/unpayed/index',
      })
    } else if (temp == "2") {
      wx.redirectTo({
        url: '../../history_order/notReceived/index',
      })
    } else if (temp == "3") {
      wx.redirectTo({
        url: '../../history_order/closed/index',
      })
    }
  },

  viewOrder: function (e) {
    wx.navigateTo({
      url: "../../order_detail/index?o_id=" + e.currentTarget.id,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var that = this;
    var c_id = wx.getStorageSync('c_id');
    wx.request({
      url: app.enabledUrl + '/mall/queryOrdersByCidAndStatus?c_id=' + c_id + "&status=unchecked",
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          orders: res.data
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