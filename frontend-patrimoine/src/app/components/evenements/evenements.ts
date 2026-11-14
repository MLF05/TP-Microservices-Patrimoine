import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EvenementService } from '../../services/evenement.service';

@Component({
  selector: 'app-evenements',
  imports: [CommonModule, FormsModule],
  templateUrl: './evenements.html',
  styleUrl: './evenements.css'
})
export class Evenements implements OnInit {
  evenements: any[] = [];
  errorMessage: string = '';
  successMessage: string = '';

  afficherFormulaire: boolean = false;
  modeEdition: boolean = false;
  evenementEnCours: any = this.videFormulaire();

  constructor(private service: EvenementService) {}

  ngOnInit(): void {
    this.charger();
  }

  videFormulaire(): any {
    return {
      id: null,
      nom: '',
      description: '',
      dateDebut: '',
      dateFin: '',
      horaire: '',
      lieu: '',
      capaciteMaximale: 0,
      placesReservees: 0
    };
  }

  charger(): void {
    this.service.getAll().subscribe({
      next: data => this.evenements = data,
      error: err => {
        console.error(err);
        this.errorMessage = 'Erreur de connexion au microservice événement';
      }
    });
  }

  ouvrirFormulaireAjout(): void {
    this.evenementEnCours = this.videFormulaire();
    this.modeEdition = false;
    this.afficherFormulaire = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  ouvrirFormulaireModif(e: any): void {
    this.evenementEnCours = { ...e };
    this.modeEdition = true;
    this.afficherFormulaire = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  fermerFormulaire(): void {
    this.afficherFormulaire = false;
    this.evenementEnCours = this.videFormulaire();
  }

  enregistrer(): void {
    if (this.modeEdition) {
      this.service.update(this.evenementEnCours.id, this.evenementEnCours).subscribe({
        next: () => {
          this.successMessage = 'Événement modifié avec succès';
          this.fermerFormulaire();
          this.charger();
        },
        error: err => {
          console.error(err);
          this.errorMessage = 'Erreur lors de la modification';
        }
      });
    } else {
      this.service.create(this.evenementEnCours).subscribe({
        next: () => {
          this.successMessage = 'Événement ajouté avec succès';
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
    if (confirm('Voulez-vous vraiment supprimer cet événement ?')) {
      this.service.delete(id).subscribe({
        next: () => {
          this.successMessage = 'Événement supprimé';
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