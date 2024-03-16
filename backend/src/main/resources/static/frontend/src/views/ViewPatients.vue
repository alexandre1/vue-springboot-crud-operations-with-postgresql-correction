<template>
    <main>
        <Navbar />
        <!-- Table-->
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1 class="text-center">View Patient</h1>
                    <!--Add button -->
                    <a href="/add" class="btn btn-primary">Add Patient</a>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Profile Image</th>
                                <th scope="col">Name</th>
                                <th scope="col">Email</th>
                                <th scope="col">Username</th>
                                <th scope="col">Phone Number</th>
                                <th scope="col">Gender</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="patient in paginatedPatients" :key="patient.id">
                                <th scope="row">{{ patient.id }}</th>
                                <td>
                                    <img :src="patient.picture" alt="Profile Image" style="max-width: 100px;">
                                </td>
                                <td>{{ patient.name }}</td>
                                <td>{{ patient.email }}</td>
                                <td>{{ patient.username }}</td>
                                <td>{{ patient.pNo }}</td>
                                <td>{{ patient.gender }}</td>
                                <td>
                                    <a class="btn btn-primary" :href="`/edit/${patient.id}`">Edit</a>
                                    <button class="btn btn-danger mx-2" @click="deletePatient(patient.id)">Delete</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <!-- Pagination component -->
                        <Pagination :current-page="currentPage" :total-pages="totalPages" @update:current-page="handlePageChange" />&nbsp;
                    </div>
            </div>
        </div>

    </main>
</template>

<script>
import Navbar from '../components/Navbar.vue';
import Pagination from '../components/PaginationView.vue';
import axios from 'axios';

export default {
    name: 'ViewPatients',
    components: {
        Navbar,
        Pagination
    },
    data() {
        return {
            patients: [],
            currentPage: 1,
            pageSize: 2
        };
    },
    computed: {
        totalPages() {
            return Math.ceil(this.patients.length / this.pageSize);
        },
        paginatedPatients() {
          const startIndex = (this.currentPage - 1) * this.pageSize;
          const endIndex = startIndex + this.pageSize;
          return this.patients.slice(startIndex, endIndex);
        }
    },
    beforeMount() {
        this.getPatients();
    },

    methods: {

    getPatients(page, size) {
        axios.get('http://localhost:8082/entities', {
            params: {
                page: page, // Send the current page number
                size: size // Send the page size
            }
        })
        .then(response => {
            // Extract patients data from the response
            const data = response.data.content;

            // Iterate through each patient and fetch the picture separately
            const promises = data.map(patient => {
                return fetch(`http://localhost:8082/patients/${patient.id}/picture`)
                    .then(res => res.blob())
                    .then(blob => {
                        // Convert blob to data URL
                        return URL.createObjectURL(blob);
                    })
                    .then(url => {
                        // Update patient object with picture URL
                        patient.picture = url; // <-- Assign the URL to picture property
                    });
            });

            // Wait for all promises to resolve
            Promise.all(promises)
                .then(() => {
                    // Set patients data after all promises are resolved
                    this.patients = data;
                });
        })
        .catch(error => {
            console.error('Error fetching patients:', error);
        });
    },

     handlePageChange(newPage) {
            this.currentPage = newPage;
            // Call getPatients method again with the updated page number
            this.getPatients();
          },
         deletePatient(id) {
            fetch(`http://localhost:8082/patient/${id}`, {
                method: 'DELETE'
            })
                .then(response => {
                    console.log(response);
                    this.getPatients();
                })
                .catch(error => {
                    console.error('Error deleting patient:', error);
                });
        }
    }
};
</script>