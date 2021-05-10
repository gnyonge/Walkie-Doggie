<template>
  <div class="column my-3">
    <bar-chart></bar-chart>
  </div>
</template>

<script>
  import BarChart from '@/components/BarChart'
  // import Reactive from '@/components/Reactive'

  export default {
    name: 'VueChartJS',
    components: {
      BarChart,
      // Reactive
    }
  }
</script>