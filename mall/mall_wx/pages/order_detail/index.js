// pages/order_detail/index.js

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageBaseURL: app.enabledUrl + '/goodsImages/',
    active: '0',
    actions: ['删除', '取消', '付款', '确认收货', '申请退货'],
    steps: [{
        text: '提交',
        desc: '已完成',
      },
      {
        text: '审核',
        desc: '描述信息',
      },
      {
        text: '付款',
        desc: '描述信息',
      },
      {
        text: '收货',
        desc: '描述信息',
      },
      {
        text: '退货',
        desc: '可选',
      },
    ],
    order_items: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    // 获取订单状态
    wx.request({
      url: app.enabledUrl + '/mall/queryOrderStatusByOid?o_id=' + options.o_id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        var steps_tmp = that.data.steps;
        for (var i = 0; i < res.data; ++i) {
          steps_tmp[i].desc = '已完成';
        }
        steps_tmp[res.data].desc = '当前';
        for (var j = res.data + 1; j < 4; ++j) {
          steps_tmp[j].desc = '等待';
        }
        that.setData({
          active: res.data,
          steps: steps_tmp
        })
      }
    });
    // 获取订单项
    wx.request({
      url: app.enabledUrl + '/mall/queryOrderItemsByOid?o_id=' + options.o_id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          order_items: res.data
        })
      }
    });
  },

  /**
   * 付款操作
   */
  pay: function () {

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