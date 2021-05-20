<template>
  <div>
    <CalendarMain /> 
  </div>
</template>

<script>
import CalendarMain from '@/components/calendar/CalendarMain'
import { mapMutations } from 'vuex'

export default {
  name: 'Calendar',
  components: {
    CalendarMain
 },
 mounted(){
    this.setNowTab(0)
  },
  methods: {
    ...mapMutations(['setNowTab'])
  },
}
</script>
