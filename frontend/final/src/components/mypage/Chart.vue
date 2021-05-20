<template>
  <div class="column my-3">
        <reactive :chart-data="datacollection"></reactive>
  </div>
</template>

<script>
  import Reactive from '@/components/Reactive'
  import { mapGetters, mapActions } from 'vuex'
  export default {
    name: 'Chart',
    components: {
      Reactive
    },
    data() {
      return {
        datacollection: {} // instantiating datacollection with null
      }
    },
    created() {
      this.statisticsInApi(this.getDogInfo.pet.peid)
      .then(() => {
        this.fillData() //anytime the vue instance is created, call the fillData() function.
      })
    },
    computed: {
      ...mapGetters(['getDogInfo', 'getStatistics'])
    },
    methods: {
      ...mapActions(['statisticsInApi']),
      fillData() {
        this.datacollection = {
          // Data for the y-axis of the chart
          labels: ['내 평균 산책횟수', '지역 평균 산책횟수', '내 평균 산책시간대', '지역 평균 산책시간대', '내 산책 총 시간', '지역 평균 산책 총 시간'],
          datasets: [
            {
              label: '산책 통계',
              backgroundColor: '#f87979',
              // Data for the x-axis of the chart
              data: [this.getStatistics.p_walk_count, this.getStatistics.l_walk_count, 
          this.getStatistics.p_walk_time, this.getStatistics.l_walk_time, this.getStatistics.p_total_time, this.getStatistics.l_total_time]
            }
          ],
          options: {
            responsive: false,
            legend: {
              labels: {
                fontColor: "red",
                fontSize: 18
              }}
            },
        }
      },
  }}
</script>
