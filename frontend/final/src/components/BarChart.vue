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
        let newarray = [this.getStatistics.p_walk_count, this.getStatistics.l_walk_count, 
        this.getStatistics.p_walk_time, this.getStatistics.l_walk_time, this.getStatistics.p_total_time, this.getStatistics.l_total_time]
        this.datacollection.datasets[0].data = this.datacollection.datasets[0].data.concat(newarray)
        console.log(this.datacollection.datasets[0].data, '??')
        this.datacollection.datasets[0].data = this.datacollection.datasets[0].data.concat(newarray)
        // console.log(test,'test')
        // this.datacollection.datasets[0][this.data] = [this.getStatistics.p_walk_count, this.getStatistics.l_walk_count, 
        // this.getStatistics.p_walk_time, this.getStatistics.l_walk_time, this.getStatistics.p_total_time, this.getStatistics.l_total_time]
        // console.log(this.datacollection.datasets[0][this.data], '어어')
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