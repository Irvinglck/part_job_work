<template>
  <div class="hello">
    <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="payId"
        label="订单号"
        width="180">
      </el-table-column>
      <el-table-column
        label="订单状态"
        width="80">
        <template slot-scope="scope">
          <span style="background-color:#909399;" v-if="scope.row.payStatus === '1'">审核中</span>
          <span style="background-color:#84CE60;" v-else-if="scope.row.payStatus === '2'">通过</span>
          <span style="background-color:red;" v-else>未通过</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="userName"
        label="账号">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名">
      </el-table-column>
      <el-table-column
        prop="mealType"
        label="套餐"
        width="50">
      </el-table-column>
      <el-table-column
        prop="accountName"
        label="账号名">
      </el-table-column>
      <el-table-column
        prop="bankName"
        label="开户行">
      </el-table-column>
      <el-table-column
        prop="cardNum"
        label="卡号">
      </el-table-column>
      <el-table-column
        size="mini"
        prop="createTime"
        label="时间">
      </el-table-column>

      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button
            size="medium"
            type="success"
            @click="handlPass(scope.$index, scope.row)">通过
          </el-button>
          <el-button
            size="medium"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">不通过
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    mounted() {
      this.$api.post('/api/advance/getAllPayOrder', {}, response => {
        if (response.status >= 0 && response.status < 300) {
          console.log(response.data);//请求成功，response为成功信息参数
          this.tableData = response.data.data;
        } else {
          console.log(response.message);//请求失败，response为失败信息
        }
      });
    },
    methods: {
      handlPass(indexD, rowDetail) {
        console.log(rowDetail)
        this.$api.post('/api/advance/updateOrderStatus', {
          payId: rowDetail.payId,
          payStatus: '2'
        }, response => {
          if (response.status >= 0 && response.status < 300) {
            this.$api.post('/api/advance/getAllPayOrder', {}, response => {
              if (response.status >= 0 && response.status < 300) {
                console.log(response.data);//请求成功，response为成功信息参数
                this.tableData = response.data.data;
              } else {
                console.log(response.message);//请求失败，response为失败信息
              }
            });
          } else {
            console.log(response.message);//请求失败，response为失败信息
          }
        });
      },
      handleDelete(indexD, rowDetail) {
        console.log(rowDetail)
        this.$api.post('/api/advance/updateOrderStatus', {
          payId: rowDetail.payId,
          payStatus: '3'
        }, response => {
          if (response.status >= 0 && response.status < 300) {
            this.$api.post('/api/advance/getAllPayOrder', {}, response => {
              if (response.status >= 0 && response.status < 300) {
                console.log(response.data);//请求成功，response为成功信息参数
                this.tableData = response.data.data;
              } else {
                console.log(response.message);//请求失败，response为失败信息
              }
            });
          } else {
            console.log(response.message);//请求失败，response为失败信息
          }
        });

      },
      tableRowClassName({row, rowIndex}) {
        if (rowIndex % 2 === 0) {
          return 'warning-row';
        } else {
          return 'success-row';
        }
      }
    },
    data() {
      return {
        tableData: []
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .hello span{
    color: white;
    display: block;
    width: 50px;
    height: 30px;
    text-align: center;
    line-height: 30px;
    border-radius: 5px;
  }

  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
