<template>
    <main>
        <Navbar />
        <div class="my-5">
            <div class="mx-auto w-25 " style="max-width:100%;">
              <h2 class="text-center mb-3">Login Patient</h2>
              <form @submit.prevent="Login">
                <!--name-->
                <div class="row">
                  <div class="col-md-12 form-group mb-3">
                    <label for="name" class="form-label">Username</label>
                    <input id="name"  type="text" name="name" class="form-control" placeholder="Username" required v-model="patient.username">
                  </div>
                </div>


                <!--Email-->
                <div class="row">
                    <div class="col-md-12 form-group mb-3">
                      <label for="email" class="form-label">Password</label>
                      <input id="email" type="password"  name="password" class="form-control" placeholder="password" required v-model="patient.password" >
                    </div>
                  </div>

                <div class="row">
                  <div class="col-md-12 form-group">
                    <input class="btn btn-primary w-100" type="submit" value="Submit">
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12 form-group">
                    <a class="nav-link" href="/forgetPassword">Forget password</a>
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

    export default {
        name: 'AddPatient',
        components: {
            Navbar
        },

        data() {
            return {
                patient : {
                    name: '',
                    email: '',
                    gender: '',
                    pNo: ''
                }
            }
        },

        methods: {
            addPatient(){
                fetch('http://localhost:8082/add', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(this.patient)
                })
                .then(data => {
                    console.log(data)
                    this.$router.push("/");
                })

            }
        },

    }


</script>