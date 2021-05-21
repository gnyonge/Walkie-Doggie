<template>
  <div>
    <div id="mainBox">
      <div id="date"><h3><b>성분 분석 결과</b></h3></div>
      <!-- 알러지 성분 구역 -->
      <div class="my-5" v-if="foodList.length != 0">
        <div class="d-flex justify-center">{{getDogInfo.pet.pe_name}}의 알러지에</div>
        <div class="d-flex justify-center">안좋은 성분이 들어있어요!</div>
      </div>
      
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
      <div v-else class="d-flex justify-space-around mt-5">
        <v-img
          max-width="100"
          max-height="100"
          src="@/assets/good.png">
        </v-img>
        <div class="my-6" style="width: 180px;">
        <div class="d-flex justify-center">{{getDogInfo.pet.pe_name}}의 알러지에</div>
        <div class="d-flex justify-center">안좋은 성분이 없어요!</div>
      </div>
      </div>
      <!-- 유해성분 구역 -->
      <div class="date mt-5" v-if="ingreList.length != 0">
        <div class="d-flex justify-center">유해성분이 들어있어요!</div>
        <div 
          class="my-5"
          max-width="344"
          max-height="344"
          outlined
          v-for="(ing, idx) in ingreList" :key="idx"
        >
          <v-list-item three-line id="healthDiv">
            <v-list-item-content>
              <div>
                <li>{{ing.i_name}}</li>
              </div>
              <div id="sub" class="mt-2">
                {{ing.i_desc}}
              </div>
            </v-list-item-content>
          </v-list-item>
        </div>
      </div>
      <div v-else class="d-flex justify-space-around mt-5">
        <v-img
          max-width="100"
          max-height="100"
          src="@/assets/good.png">
        </v-img>
        <div class="my-9" style="width: 180px;">
          <div class="d-flex justify-center">유해성분이 없어요!</div>
        </div>
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
      foodList: [],
      ingreList: [],
    }
  },
  computed: {
    ...mapGetters(['getDogInfo', 'getFoodResult', 'getUser'])
  },
  created() {
    if (this.getUser == undefined) {
        alert('로그인 해주세요!')
        this.$router.push('/')
      } else {
        this.foodList = this.getFoodResult.Allergys
        this.ingreList = this.getFoodResult.Ingredients
      }
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
#sub {
  font-size: 0.875rem;
  color: rgba(0, 0, 0, 0.6);
  line-height: 1.3;
  white-space: initial;
  text-overflow: ellipsis;
}
</style>