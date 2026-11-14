import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CircuitService } from '../../services/circuit.service';

@Component({
  selector: 'app-circuits',
  imports: [CommonModule, FormsModule],
  templateUrl: './circuits.html',
  styleUrl: './circuits.css'
})
export class Circuits implements OnInit {
  circuits: any[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  afficherFormulaire: boolean = false;
  modeEdition: boolean = false;
  circuitEnCours: any = this.videFormulaire();

  constructor(private service: CircuitService) {}

  ngOnInit(): void {
    this.charger();
  }

  videFormulaire(): any {
    return {
      id: null,
      nom: '',
      description: '',
      dureeEstimee: 0,
      difficulte: 'FACILE',
      distance: 0,
      etapes: '',
      pointsInteret: ''
    };
  }

  charger(): void {
    this.service.getAll().subscribe({
      next: data => this.circuits = data,
      error: err => {
        console.error(err);
        this.errorMessage = 'Erreur de connexion au microservice circuit';
      }
    });
  }

  ouvrirFormulaireAjout(): void {
    this.circuitEnCours = this.videFormulaire();
    this.modeEdition = false;
    this.afficherFormulaire = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  ouvrirFormulaireModif(c: any): void {
    this.circuitEnCours = { ...c };
    this.modeEdition = true;
    this.afficherFormulaire = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  fermerFormulaire(): void {
    this.afficherFormulaire = false;
    this.circuitEnCours = this.videFormulaire();
  }

  enregistrer(): void {
    if (this.modeEdition) {
      this.service.update(this.circuitEnCours.id, this.circuitEnCours).subscribe({
        next: () => {
          this.successMessage = 'Circuit modifié avec succès';
          this.fermerFormulaire();
          this.charger();
        },
        error: err => {
          console.error(err);
          this.errorMessage = 'Erreur lors de la modification';
        }
      });
    } else {
      this.service.create(this.circuitEnCours).subscribe({
        next: () => {
          this.successMessage = 'Circuit ajouté avec succès';
          this.fermerFormulaire();
          this.charger();
        },
        error: err => {
          console.error(err);
          this.errorMessage = 'Erreur lors de la création';
        }
      });
    }
  }

  supprimer(id: number): void {
    if (confirm('Voulez-vous vraiment supprimer ce circuit ?')) {
      this.service.delete(id).subscribe({
        next: () => {
          this.successMessage = 'Circuit supprimé';
          this.charger();
        },
        error: err => {
          console.error(err);
          this.errorMessage = 'Erreur lors de la suppression';
        }
      });
    }
  }
}