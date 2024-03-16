<template>
  <main>
    <Navbar />
    <div class="my-5">
      <div class="mx-auto w-25 " style="max-width:100%;">
        <h2 class="text-center mb-3">Retrieve Passwords</h2>
        <form @submit.prevent="forgetPassword">
          <!--name-->
          <!--Email-->
          <div class="row">
            <div class="col-md-12 form-group mb-3">
              <label for="email" class="form-label">Email</label>
              <input id="email" type="email"  name="email" class="form-control" placeholder="email" required v-model="patient.email" >
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 form-group">
              <input class="btn btn-primary w-100" type="submit" value="Submit">
            </div>
          </div>

          <div>

          </div>
        </form>

      </div>
    </div>
  </main>
</template>


<script>
import Navbar from '../components/Navbar.vue';
import axios from 'axios';

export default {
  name: 'forgetPassword',
  components: {
    Navbar
  },

  data() {
    return {
      patient: {
        email: ''
      }
   }
  }};

</script>
