<template>
  <div>
    <FoodMain />
  </div>
</template>

<script>
import FoodMain from '@/components/food/FoodMain'
import { mapMutations } from 'vuex'

export default {
  name: 'Food',
  components: {
    FoodMain
  },
  mounted(){
    this.setNowTab(2)
  },
  methods: {
    ...mapMutations(['setNowTab'])
  },
}
</script>
