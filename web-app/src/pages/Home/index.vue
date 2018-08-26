<template>
  <div id="home">
    <Menu/>    
    <MonthTab/>
  </div>
</template>

<script>
import Menu from '@/components/Menu'
import MonthTab from './components/MonthTab'

export default {
  name: 'Home', 
  components: { Menu, MonthTab }
}
</script>