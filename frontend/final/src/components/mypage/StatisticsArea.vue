<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        통계
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <div>
      <Chart />
    </div>
  </div>
</template>

<script>
import Chart from '@/components/mypage/Chart'
export default {
  name: "StatisticsArea",
  components: {
    Chart,
  },
  methods: {
    goback() {
      this.$router.push('/mypage')
    }
  }
}
</script>
<style scoped>
.logo {
  width: 150px;
  height: 150px; 
  border-radius: 70%;
  overflow: hidden;
  border: solid 3px;
  /* display: flex; */
  /* justify-content: center; */
  /* align-items: center; */
  
}
.mypage-list {
  width: 100%;
  height: 50px;
  border-radius: 0;
  box-shadow: 0;
  background: #DEB98B;
}
.mypage-list-btn {
  box-shadow: 0;
  background: #DEB98B;
}
.health-box {
  padding: 25px;
  
  height: 80%;
  background: #EFE8DE;
  margin: 10px;
}
.lst {
  width: 100%;
  height: 95px;
  background: #EFE8DE;
  font-size: 40px;
}
p {
  color: black;
  font-size: 40px;
}
</style>