<script>
import { Bar } from 'vue-chartjs'

export default {
  extends: Bar,
  props: ['label', 'labels', 'data'],
  mounted () {
    this.renderChart({
      labels: this.$props.labels,
      datasets: [
        {
          label: this.$props.label,
          backgroundColor: '#009688',
          data: this.$props.data
        }
      ]
    })
  }
}
</script>
