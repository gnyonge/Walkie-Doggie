<template>
  <div>
    <CalendarMain v-if="this.$route.path=='/calendar' || this.$route.path=='/'"/> 
    <CalendarWrite v-else-if="this.$route.path=='/calendar/write'"/>
    <CalendarDetail v-else-if="this.$route.path=='/calendar/todaywalk'" />
  </div>
</template>

<script>
import CalendarMain from '@/components/calendar/CalendarMain'
import CalendarWrite from '@/components/calendar/CalendarWrite'

export default {
  name: 'Calendar',
  components: {
    CalendarMain,
    CalendarWrite,
  }
}
</script>
