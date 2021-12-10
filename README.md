# HITSE-SPT-Project

## 项目计划

### 统计功能

- 库存统计
- 销售统计
- 经营状况统计
- ...至少两个

### 库存管理

- 多库问题
- 添加、删除库、设置库优先级

### 销售管理

- **批发销售单状态问题**
- 销售单审核时出库问题？默认系统策略：优先级 + **人为修改**？
- 零售可以有挂起功能，先给别人结账（相当于零售草稿单，好像没有组做）
- **建议**单独做收款单（客户ID、销售单编号、收款金额）以实现多次收款，还会支持预收款、批量收款等 **艹**（可考虑）

### 用户管理

**权限具体设计：**
INT 32位作为标记，仍可拓展，也可以考虑分类：

>
> 1. 统计查看 +1
> 2. 货品资料管理 +2
> 3. 货品入库 +4
> 4. 库存盘点 +8
> 5. 库存调度 +16
> 6. 开批发销售单 +32
> 7. POS零售 +64
> 8. 批发销售单审核/退款审核 +128
> 9. 批发销售单收款/退款 +256
> 10. 客户管理 +512
> 11. 系统用户管理 +1024

### 文档

- 系统分析与设计说明书
- 系统使用说明书

> 内容有亿点多...
