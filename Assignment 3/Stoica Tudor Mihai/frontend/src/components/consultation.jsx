import tr from "react";
import TextField from "@material-ui/core/TextField";
// import Modal from "react-modal";
import { Button } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.css";
import React, { Component } from "react";
import ConsultationModal from "./consultationModal";

class Consultation extends Component {
  state = {
    modalIsOpen: false,

    consultation: {
      id: 1,
      date: "01-01-2000",
      details: "mai are 3 luni de trait",
      doctor: "Dr. Strange",
      patient: "Minerva",
    },
  };

  fetchStateData() {
    // use {this.props.patient}
  }

  handleOpenModal = () => {this.setState({ modalIsOpen: true }); console.log("handleOpenModal");}

  handleCloseModal = () => {this.setState({ modalIsOpen: false });console.log("handleCloseModal");};

  render() {
    return (
      <div>
        <Button variant="primary" onClick={this.handleOpenModal}>
          Consultations
        </Button>
        <ConsultationModal
          modalIsOpen={this.state.modalIsOpen}
          patientId={this.state.consultation.id}
          closeModal={this.handleCloseModal}
        />
      </div>
    );
  }
}

export default Consultation;
