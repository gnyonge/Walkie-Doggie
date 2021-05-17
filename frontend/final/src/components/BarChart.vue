<script>
  //Importing Bar class from the vue-chartjs wrapper
  import { Bar } from 'vue-chartjs'
  import { mapGetters, mapActions } from 'vuex'
  //Exporting this so it can be used in other components
  export default {
    extends: Bar,
    created() {
      this.statisticsInApi(this.getDogInfo.pet.peid)
      .then(() => {
        var stat = this.getStatistics
        this.datacollection.datasets[0].data = [stat.p_walk_count, stat.l_walk_count, 
        stat.p_walk_time, stat.l_walk_time, stat.p_total_time, stat.l_total_time]
      })
    },
    computed: {
      ...mapGetters(['getDogInfo', 'getStatistics'])
    },
    data () {
      return {
        datacollection: {
          //Data to be represented on x-axis
          labels: ['내 평균 산책횟수', '지역 평균 산책횟수', '내 평균 산책시간대', '지역 평균 산책시간대', '내 산책 총 시간', '지역 평균 산책 총 시간'],
          datasets: [
            {
              label: 'Data One',
              backgroundColor: '#f87979',
              pointBackgroundColor: 'white',
              borderWidth: 1,
              pointBorderColor: '#249EBF',
              //Data to be represented on y-axis
              data: []
            }
          ]
        },
        //Chart.js options that controls the appearance of the chart
        options: {
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              },
              gridLines: {
                display: true
              }
            }],
            xAxes: [ {
              gridLines: {
                display: false
              }
            }]
          },
          legend: {
            display: true
          },
          responsive: true,
          maintainAspectRatio: false
        }
      }
    },
    mounted () {
      //renderChart function renders the chart with the datacollection and options object.
      this.renderChart(this.datacollection, this.options)
    },
    methods: {
      ...mapActions(['statisticsInApi'])
    }
  }
</script>