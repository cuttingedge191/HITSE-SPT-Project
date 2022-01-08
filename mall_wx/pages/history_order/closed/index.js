// pages/history_order/unpayed/index.js

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    active: 3,
    orders: []
  },

  changePage: function (event) {
    var temp = event.detail.name;
    if (temp == "0") {
      wx.redirectTo({
        url: '../../history_order/unchecked/index',
      })
    } else if (temp == "2") {
      wx.redirectTo({
        url: '../../history_order/notReceived/index',
      })
    } else if (temp == "1") {
      wx.redirectTo({
        url: '../../history_order/unpayed/index',
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
    // 添加已完成订单
    wx.request({
      url: app.enabledUrl + '/mall/queryOrdersByCidAndStatus?c_id=' + c_id + "&status=closed",
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          orders: that.data.orders.concat(res.data)
        })
      }
    });
    // 添加已退款订单
    wx.request({
      url: app.enabledUrl + '/mall/queryOrdersByCidAndStatus?c_id=' + c_id + "&status=received",
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          orders: that.data.orders.concat(res.data)
        })
      }
    });
    // 添加未通过审核订单
    wx.request({
      url: app.enabledUrl + '/mall/queryOrdersByCidAndStatus?c_id=' + c_id + "&status=failed",
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          orders: that.data.orders.concat(res.data)
        })
      }
    });
    // 退货中的订单
    wx.request({
      url: app.enabledUrl + '/mall/queryOrdersByCidAndStatus?c_id=' + c_id + "&status=refund",
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          orders: that.data.orders.concat(res.data)
        })
      }
    });
    // 添加未通过审核订单
    wx.request({
      url: app.enabledUrl + '/mall/queryOrdersByCidAndStatus?c_id=' + c_id + "&status=returned",
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          orders: that.data.orders.concat(res.data)
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