// pages/order_detail/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageURL: "https://img.yzcdn.cn/vant/ipad.jpeg",
    active:2,
    actions: [false, false, true, false, false],
    steps: [
      {
        text: '提交',
        desc: '描述信息',
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
        text: '到货',
        desc: '描述信息',
      },
      {
        text: '退货',
        desc: '描述信息',
      },
    ],
    order_items: [
      {
        id: 1,
        num: 3,
        price: 56.00,
        description: "test",
        name: "APPLE IPAD 2021 pro",
      },
      {
        id: 2,
        num: 3,
        price: 56.00,
        description: "test",
        name: "APPLE IPAD 2021 pro",
      },
    ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log("----获得订单ID----",options.o_id);
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