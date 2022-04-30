import React, { Component } from "react";
import { Modal, Button } from "react-bootstrap";

class ConsultationModal extends Component {
  state = {
    modalIsOpen: false,
    patientId: 1,
  };

  fetchPatientConsultations() {

  }

  render() {
    {
      this.fetchPatientConsultations();
    }

    return (
      <Modal show={this.props.modalIsOpen} onHide={this.props.closeModal}>
        <Modal.Header closeButton>
          <Modal.Title>Modal heading</Modal.Title>
        </Modal.Header>
        <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={this.props.closeModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    );
  }
}

export default ConsultationModal;
