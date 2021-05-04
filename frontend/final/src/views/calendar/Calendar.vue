<template>
  <div>
    <CalendarMain v-if="this.$route.path=='/calendar'"/> 
    <CalendarWrite v-else-if="this.$route.path=='/calendar/write'"/>
  </div>
</template>

<script>
import CalendarMain from '@/components/calendar/CalendarMain'
import CalendarWrite from '@/components/calendar/CalendarWrite'
export default {
  name: 'Calendar',
  components: {
    CalendarMain,
    CalendarWrite
  }
}
</script>
