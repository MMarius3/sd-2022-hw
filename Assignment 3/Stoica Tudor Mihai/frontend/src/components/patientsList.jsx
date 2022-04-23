import React, { Component } from "react";
import Patient from "./patient";

class PatientsList extends Component {
  state = {
    patients: [
      {
        id: 1,
        name: "george",
        personalNumericalCode: "123asfdcasd213",
        birthDate: "01-01-2000",
        address: "observator",
      },
      {
        id: 2,
        name: "marius",
        personalNumericalCode: "zxcxzceqrawe",
        birthDate: "01-01-2100",
        address: "ceahlau",
      },
    ],
  };

  updateName(patients, index, newTextField) {
    patients[index].name = newTextField;
    return patients;
  }

  updatePersonalNumericCode(patients, index, newTextField) {
    patients[index].personalNumericalCode = newTextField;
    return patients;
  }

  updateBirthDate(patients, index, newTextField) {
    patients[index].birthDate = newTextField;
    return patients;
  }

  updateAddress(patients, index, newTextField) {
    patients[index].address = newTextField;
    return patients;
  }

  handleTextFieldChange = (patient, newTextField, patientsAfterFieldChange) => {
    const patients = [...this.state.patients];
    const index = patients.indexOf(patient);
    patients[index] = { ...patient };
    const updatedPatientsList = patientsAfterFieldChange(
      patients,
      index,
      newTextField
    );
    this.setState({ patients: updatedPatientsList });
  };

  handleNameChange = (patient, newTextField) => {
    this.handleTextFieldChange(patient, newTextField, this.updateName);
  };

  handlePersonalNumericCodeChange = (patient, newTextField) => {
    this.handleTextFieldChange(
      patient,
      newTextField,
      this.updatePersonalNumericCode
    );
  };

  handleBirthDateChange = (patient, newTextField) => {
    this.handleTextFieldChange(patient, newTextField, this.updateBirthDate);
  };

  handleAddressChange = (patient, newTextField) => {
    this.handleTextFieldChange(patient, newTextField, this.updateAddress);
  };

  handleDeletePatient = (patientId) => {
    const patients = this.state.patients.filter(
      (currentPatient) => currentPatient.id !== patientId
    );
    this.setState({ patients });
  };

  fetchPatients() {
    fetch("http://localhost:8080/clinic/administrator/getUserAccountById", {
      method: "GET",
    })
      .then((responseData) => {
        console.log(responseData);
        this.state.patients = responseData;
      })
      .catch((error) => console.warn(error));
  }

  render() {
    // this.fetchPatients();
    return (
      <React.Fragment>
        <table class="table">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col"></th>
              <th scope="col">ID</th>
              <th scope="col">Name</th>
              <th scope="col">Personal Numerical Code</th>
              <th scope="col">Birth Date</th>
              <th scope="col">Address</th>
            </tr>
          </thead>
          <tbody>
            {this.state.patients.map((currentPatient) => (
              <Patient
                patient={currentPatient}
                id={currentPatient.id}
                name={currentPatient.name}
                personalNumericalCode={currentPatient.personalNumericalCode}
                birthDate={currentPatient.birthDate}
                address={currentPatient.address}
                onNameChange={this.handleNameChange}
                onPersonalNumericCodeChange={
                  this.handlePersonalNumericCodeChange
                }
                onBirthDateChange={this.handleBirthDateChange}
                onAddressChange={this.handleAddressChange}
                onDelete={this.handleDeletePatient}
              ></Patient>
            ))}
          </tbody>
        </table>

        <button
          className="btn btn-primary btn-sm m-2"
          onClick={() => {
            // API call to backend with the patients list -> we will update it
          }}
        >
          Add New Patient
        </button>

        <button
          className="btn btn-primary btn-sm m-2"
          onClick={() => {
            // API call to backend with the patients list -> we will update it
          }}
        >
          Save Changes
        </button>
      </React.Fragment>
    );
  }
}

export default PatientsList;
