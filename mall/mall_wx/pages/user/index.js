// pages/user/index.js

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
    src: "",
    unc_cnt: "",
    unp_cnt: "",
    unr_cnt: ""
  },

  changeInfo: function () {
    wx.navigateTo({
      url: '/pages/userInfo/index',
    })
  },

  logout: function () {
    wx.clearStorageSync();
    wx.redirectTo({
      url: '/pages/index/index'
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var that = this;
    var c_id = wx.getStorageSync('c_id');
    var cType = wx.getStorageSync('c_type');
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
          type: cType == 'retail' ? '零售' : '批发',
          src: res.data.gender == 'male' ?  app.maleImage : app.femaleImage
        })
      }
    });
    wx.request({
      url: app.enabledUrl + '/mall/getOrderCntByCid?c_id=' + c_id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        that.setData({
          unc_cnt: res.data[0] == '0' ? '' : res.data[0],
          unp_cnt: res.data[1] == '0' ? '' : res.data[1],
          unr_cnt: res.data[2] == '0' ? '' : res.data[2]
        })
      }
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
    this.onLoad();
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