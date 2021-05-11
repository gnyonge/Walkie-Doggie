<template>
  <div id="mainBox">
    <div class="d-flex justify-space-between">
      <div>
      <v-icon @click="goback()">mdi-arrow-left</v-icon>
      </div>
      <div>
        건강사항
      </div>
      <div style="width: 24px; background-color: white;">
      </div>
    </div>
    <div
      class="my-5"
      max-width="344"
      outlined
      v-for="i, idx in 3" 
      :key="idx"
    >
      <v-list-item three-line id="healthDiv">
        <v-list-item-content>
          <div>
            2월 1일
          </div>
          <v-list-item-subtitle>
            <li>예방접종 1차</li>
            <li>심장사상충 약</li>
          </v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-avatar
          tile
          size="60"
          color="grey"
        ></v-list-item-avatar>
      </v-list-item>
    </div>
  </div>
</template>

<script>
export default {
  name: "HealthArea",
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
#healthDiv {
  border-radius: 15px;
  background-color: white;
  border: 1px solid #E5E5E5;
  box-shadow: 3px 3px 3px #E5E5E5;
}
</style>