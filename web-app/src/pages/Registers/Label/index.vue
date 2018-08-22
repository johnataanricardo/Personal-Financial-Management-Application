<template>
  <div id="home">
    <Menu/>    
  </div>
</template>

<script>
import Menu from '@/components/Menu'
import axios from 'axios'

const api = process.env.API_URL

export default {
  name: 'Home', 
  components: { Menu },
  data: () => ({    
  }),
  methods: {
    
  }
}
</script>

<style scoped>

</style>