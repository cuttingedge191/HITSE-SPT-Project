// pages/history_order/unchecked/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    active:0,
      orders:[
       {
        order_id:'1',
        order_number:'234242414',
        order_price:'666',
        order_time:'2021.12.12',
       },
       {
        order_id:'1',
        order_number:'234242414',
        order_price:'666',
        order_time:'2021.12.12',
       },
       {
        order_id:'1',
        order_number:'234242414',
        order_price:'666',
        order_time:'2021.12.12',
       },
       {
        order_id:'1',
        order_number:'234242414',
        order_price:'666',
        order_time:'2021.12.12',
       },
       {
        order_id:'1',
        order_number:'234242414',
        order_price:'666',
        order_time:'2021.12.12',
       },
       {
        order_id:'1',
        order_number:'234242414',
        order_price:'666',
        order_time:'2021.12.12',
       }
     ]
  },
  changePage: function(event) {
    var temp=event.detail.name;
    if (temp=="1") {
      wx.redirectTo({
        url: '../../history_order/unpayed/index',
      })
    } else if(temp=="2") {
      wx.redirectTo({
        url: '../../history_order/notReceived/index',
      })
    }
    else if(temp=="3"){
      wx.redirectTo({
        url: '../../history_order/closed/index',
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