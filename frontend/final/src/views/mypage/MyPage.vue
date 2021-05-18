<template>
  <div>
    <MyPageMain />
  </div>
</template>

<script>
import MyPageMain from "@/components/mypage/MyPageMain.vue";
import { mapMutations } from 'vuex'

export default {
  name: 'MyPage',
  components: {
    MyPageMain
  },
  mounted(){
    this.setNowTab(3)
  },
  methods: {
    ...mapMutations(['setNowTab'])
  },
}
</script>
