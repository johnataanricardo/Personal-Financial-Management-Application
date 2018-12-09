<script>
import { Bar } from 'vue-chartjs'

export default {
  extends: Bar,
  props: ['label', 'labels'],
  ref: 'chart',
  mounted () {
    this.fillData([])
  },
  methods: {
    fillData(data) {
      this.renderChart({
        labels: this.$props.labels,
        datasets: [
          {
            label: this.$props.label,
            backgroundColor: '#009688',
            data: data
          }
        ]
      })
    }
  }
}
</script>
