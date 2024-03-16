<template>
  <main>
    <Navbar />
    <div class="my-5">
      <div class="mx-auto w-25 " style="max-width:100%;">
        <h2 class="text-center mb-3">Retrieve Password</h2>
        <form @submit.prevent="forgetPassword">
          <div class="row">
            <div class="col-md-12 form-group mb-3">
              <label for="email" class="form-label">Email</label>
              <input id="email" type="email"  name="email" class="form-control" placeholder="email" required v-model="patient.email" >
            </div>
          </div>
          <div class="row">
            <div class="col-md-12 form-group">
              <button class="btn btn-primary w-100" @click="forgetPassword(patient.email)">Submit</button>
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
  },
  methods: {
    forgetPassword() {
      axios.put(`http://localhost:8082/forgetPassword/${this.patient.email}`, this.patient)
          .then(response => {
            if (response.status === 200) {
              console.log('Password successfully retrieved');
              this.$router.push("/");
            } else {
              console.error('Failed to retrieve patient');
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
    }
  }
};

</script>
