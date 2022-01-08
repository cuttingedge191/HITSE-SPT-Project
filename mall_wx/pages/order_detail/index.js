// pages/order_detail/index.js
import Toast from '../../libs/dist/toast/toast';
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageBaseURL: app.enabledUrl + '/goodsImages/',
    active: '0',
    status: '0',
    actions: ['删除', '取消', '付款', '确认收货', '申请退货'],
    icons: ["cross", "cross", "balance-o", "success", "balance-o"],
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
      {
        text: '完成',
        desc: '描述信息',
      },
    ],
    order_items: [],
    o_id: 0,
    show: false,
  },
  /**
   * 关闭未审核订单
   */
  editOrder: function () {
    var that = this;
    var status;
    // 未审核，直接关闭订单
    if (that.data.status == '1')
      status = 'failed';
    // 选择付款方式
    else if (that.data.status == '2' && that.data.show == false) {
      that.setData({
        show: true,
      })
      return;
    } else if (that.data.status == '2' && that.data.show == true) { // 付款
      that.setData({
        show: false,
      })
      status = 'paid';
    } else if (that.data.status == '3') { //确认收货
      status = 'received';
    } else if (that.data.status == '0' || that.data.status == '6' || that.data.status == '7') { //failed 删除订单
      status = 'delete';
    } else if (that.data.status == '4') { //申请退货
      status = 'refund';
    }
    wx.request({
      url: app.enabledUrl + '/mall/editOrder?o_id=' + that.data.o_id + '&status=' + status,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data == 'failed') {
          wx.switchTab({
            url: '/pages/user/index'
          });
        } else if (res.data == 'paid') {
          Toast.success('已支付！');
          setTimeout(function () {
            wx.switchTab({
              url: '/pages/user/index'
            })
          }, 700);
        } else if (res.data == 'received') {
          Toast.success('已收货！');
          setTimeout(function () {
            wx.switchTab({
              url: '/pages/user/index'
            })
          }, 700);
        } else if (res.data == 'delete') {
          Toast.success('已删除！');
          setTimeout(function () {
            wx.switchTab({
              url: '/pages/user/index'
            })
          }, 700);
        } else if (res.data == 'refund') {
          Toast.success('正在申请退货！');
          setTimeout(function () {
            wx.switchTab({
              url: '/pages/user/index'
            })
          }, 700);
        }
      }
    });
  },

  closePopup: function () {
    var that = this;
    that.setData({
      show: false,
    })
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
        var tmp_res = res.data
        if (tmp_res == 5) tmp_res = 4;
        if (tmp_res == 6 || tmp_res == 7) tmp_res = 5;
        for (var i = 0; i < tmp_res; ++i) {
          steps_tmp[i].desc = '已完成';
        }
        steps_tmp[tmp_res].desc = '当前';
        if (res.data == 6) steps_tmp[tmp_res].desc = '已退款';
        if (res.data == 7) steps_tmp[tmp_res].desc = '订单关闭';
        for (var j = tmp_res + 1; j <= 5; ++j) {
          steps_tmp[j].desc = '等待';
        }
        that.setData({
          active: tmp_res,
          status: res.data,
          steps: steps_tmp,
          o_id: options.o_id,
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