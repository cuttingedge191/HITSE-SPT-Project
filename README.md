# HITSE-SPT-Project

## 项目计划

### 库存管理

- ~~进货入库似乎也没有？ InventoryMapper update~~
- ~~货品成本价更新问题~~
- ~~调度（源->目的）实现~~
- 多个库查询（下拉加多选） 【货品名称  数量】 + 详细信息界面（成本价 总数 各库数量）
- ~~库存二级目录：调度 库存盘点（多库）~~

### 销售管理

- 销售单审核时出库问题？默认系统策略：优先级 + 人为修改？
- ~~订单货品合并~~
- 销售单提交实现
- 销售单状态：草稿draft、待审核unchecked、审核不通过failed、已审核checked、已付款paid（零售）、待退款、已退款
- ~~零售单放到POS~~
- **POS一定要有扫条码，用手工输入模拟**，界面需要添加
- 零售可以有挂起功能，先给别人结账（相当于零售草稿单）
- **建议**单独做收款单（客户ID、销售单编号、收款金额）以实现多次收款，还会支持预收款、批量收款等 **艹**

### 用户管理

- ~~界面~~
- ~~权限实现（先hidden），老师建议定义角色，方便权限管理~~
- ~~权限：Admin【全部权限】 > 经理 >= 店长 >= 普通员工~~
- 部分按钮的权限还没有设置

> 权限具体设计：
> INT 32位作为标记
> 1. 统计
> 2. 货品资料管理
> 3. 货品入库
> 4. 库存盘点
> 5. 库存调度
> 6. 开批发销售单
> 7. POS零售
> 8. 批发销售单审核/退款审核
> 9. 批发销售单收款/退款
> 10. 客户管理
> 11. 系统用户管理

### 文档

- ~~需求描述~~
- ~~需求分析建模~~
- 类图需要再调整
- 系统设计

### 其他事项

- 用户注册？
- 用户登录后的问候语和身份展示？Session？
- html文件和mapping命名修改(Refactor?)
- 可以先不考虑多用户并行冲突问题
