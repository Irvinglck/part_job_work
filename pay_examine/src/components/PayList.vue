<template>
  <div class="hello">
    <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName">
      <el-table-column
        prop="date"
        label="订单号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="订单状态"
        width="180">
      </el-table-column>
      <el-table-column
        prop="date"
        label="账号">
      </el-table-column>
      <el-table-column
        prop="date"
        label="姓名">
      </el-table-column>
      <el-table-column
        prop="date"
        label="套餐">
      </el-table-column>
      <el-table-column
        prop="date"
        label="账号名">
      </el-table-column>
      <el-table-column
        prop="date"
        label="开户行">
      </el-table-column>
      <el-table-column
        prop="date"
        label="卡号">
      </el-table-column>
      <el-table-column
        size="mini"
        prop="date"
        label="时间">
      </el-table-column>

      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button
            size="medium"
            type="success"
            @click="handleEdit(scope.$index, scope.row)">通过</el-button>
          <el-button
            size="medium"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)">不通过</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  export default {
    methods: {
      handleEdit(indexD,rowDetail){
        console.log(indexD+",,,"+rowDetail)
        this.$api.get('/lyuan/login', {
          "username": "lck",
          "password": "123456"
        }, response => {
          if (response.status >= 200 && response.status < 300) {
            console.log(response.data);//请求成功，response为成功信息参数
          } else {
            console.log(response.message);//请求失败，response为失败信息
          }
        });
      },
      tableRowClassName({row, rowIndex}) {
        if (rowIndex %2===0) {
          return 'warning-row';
        } else{
          return 'success-row';
        }
      },
      // handleEdit(index, row) {
      //   console.log(index, row);
      // },
      handleDelete(index, row) {
        console.log(index, row);
        console.log(index, row.address);

      }
    },
    data() {
      return {
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
        }, {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
        }, {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄'
        }]
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
