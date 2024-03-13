<template>
    <main>
        <Navbar/>
        <div class="my-5">
            <div class="mx-auto w-25 " style="max-width:100%;">
              <h2 class="text-center mb-3">Update Patient</h2>

              <form @submit.prevent="updatedPatient">

                <!--name-->
                <div class="row">
                  <div class="col-md-12 form-group mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input id="name"  type="text" name="name" class="form-control" placeholder="Name" required v-model="patient.name">
                  </div>
                </div>

                
                <!--Email-->
                <div class="row">
                    <div class="col-md-12 form-group mb-3">
                      <label for="email" class="form-label">Email</label>
                      <input id="email" type="email"  name="email" class="form-control" placeholder="email" required v-model="patient.email" >
                    </div>
                  </div>

                <!--Phone Number-->
                <div class="row">
                    <div class="col-md-12 form-group mb-3">
                      <label for="pNo" class="form-label">Phone Number</label>
                      <input id="pNo" type="text"  name="pNo" class="form-control" placeholder="Phone Number" required v-model="patient.pNo" >
                    </div>
                  </div>             
                <!--Username-->
                <div class="row">
                    <div class="col-md-12 form-group mb-3">
                      <label for="pNo" class="form-label">Username</label>
                      <input id="pNo" type="text"  name="pNo" class="form-control" placeholder="Username" required v-model="patient.username" >
                    </div>
                  </div>

                <!--Password-->
                <div class="row">
                    <div class="col-md-12 form-group mb-3">
                      <label for="password" class="form-label">Password</label>
                      <input id="pNo" type="password"  name="pNo" class="form-control" placeholder="Password" required v-model="patient.password" >
                    </div>
                  </div>

                <div class="row">
                    <div class="col-md-12 form-group mb-3">
                      <label for="picture" class="form-label">Profil Image</label>
                      <input id="picture" type="file"  name="picture" class="form-control" @change="handleFileChange">
                      <img v-if="selectedFile" :src="imagePreview" alt="Selected Image" class="mt-2" style="max-width: 100%;">
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
    name: 'UpdatePatient',
    components: {
        Navbar
    },

    data() {
        return {
            patient: {
                name: '',
                email: '',
                gender: '',
                pNo: '',
                username: '',
                password: ''
            },
            selectedFile: null,
            imagePreview: null
        }
    },
    beforeMount(){
        this.getPatients();
    },

    methods: {

        handleFileChange(event) {
            this.selectedFile = event.target.files[0];
            this.imagePreview = URL.createObjectURL(this.selectedFile);
        },

        getPatients(){
            fetch(`http://localhost:8082/patient/${this.$route.params.id}`)
            .then(res => res.json())
            .then(data => {
                this.patient = data;
                console.log(this.patient);
            })

        },

        updatedPatient() {
            const formData = new FormData();
            formData.append('file', this.selectedFile);
            // Append patient data as JSON
            formData.append('patient', JSON.stringify(this.patient));

            axios.put(`http://localhost:8082/patient/${this.$route.params.id}`, formData)
                .then(response => {
                    if (response.status === 200) {
                        console.log('Patient updated successfully');
                        this.$router.push("/");
                    } else {
                        console.error('Failed to update patient');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }

    }}

</script>