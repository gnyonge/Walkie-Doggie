<template>
  <div>
    <div id="mainBox">
      <div id="date"><h3>성분 분석 결과</h3></div>
      <div id="date" class="my-5" v-if="foodList.length != 0"><p>{{getDogInfo.pet.pe_name}}에게 맞지 않는 성분이 들어있어요!</p></div>
      <div id="date" class="my-5" v-else><p>{{getDogInfo.pet.pe_name}}에게 안전한 성분들이에요!</p></div>
      <div v-if="foodList.length != 0">
      <div
        class="my-5"
        max-width="344"
        outlined
        v-for="(food, idx) in foodList" :key="idx"
      >
        <v-list-item three-line id="healthDiv">
          <v-list-item-content>
              <li class="mt-1">{{food}}</li>
            <!-- <v-list-item-subtitle> -->
            <!-- </v-list-item-subtitle> -->
          </v-list-item-content>
          <v-list-item-avatar tile size="50">
            <img
              src="@/assets/bad.png"
              alt="건강사진"
            >
          </v-list-item-avatar>
        </v-list-item>
      </div>
      </div>
      <div v-else class="d-flex justify-center">
        <v-img
          width="280"
          src="@/assets/good.png">
        </v-img>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
export default {
  name: "FoodResult",
  data() {
    return {
      foodList: []
    }
  },
  computed: {
    ...mapGetters(['getDogInfo', 'getFoodResult'])
  },
  created() {
    console.log(this.getFoodResult)
    this.foodList = this.getFoodResult.Allergys
  }
}
</script>

<style>
#healthDiv {
  border-radius: 15px;
  background-color: white;
  border: 1px solid #E5E5E5;
  box-shadow: 3px 3px 3px #E5E5E5;
}
</style>