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
      v-for="arr, date in allHealthList" :key="date.idx"
    >
      <v-list-item three-line id="healthDiv">
        <v-list-item-content>
          <div>
            {{date}}
          </div>
          <v-list-item-subtitle v-for="i in arr" :key="i.idx">
            <li class="mt-1">{{i}}</li>
          </v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-avatar tile size="70">
          <img
            src="@/assets/health.png"
            alt="건강사진"
          >
        </v-list-item-avatar>
      </v-list-item>
    </div>
  </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex'
export default {
  name: "HealthArea",
  data() {
    return {
      allHealthList: {},
    }
  },
  created() {
    this.allHealthListInApi(this.getDogInfo.pet.peid)
    .then(() => {
      let tempList = {}
      for (let i of this.getAllHealthList) { 
        if (Object.keys(tempList).includes(i.h_date.slice(0,10))) {
          tempList[i.h_date.slice(0, 10)].push(i.h_content)
        } else {
          tempList[i.h_date.slice(0, 10)] = [i.h_content]
        }
      }
      this.allHealthList = tempList
    })
  },
  computed: {
    ...mapGetters(['getDogInfo', 'getAllHealthList'])
  },
  methods: {
    ...mapActions(['allHealthListInApi']),
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