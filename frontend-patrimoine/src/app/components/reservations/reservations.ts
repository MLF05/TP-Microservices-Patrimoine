import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservationService } from '../../services/reservation.service';
import { EvenementService } from '../../services/evenement.service';
import { CircuitService } from '../../services/circuit.service';

@Component({
  selector: 'app-reservations',
  imports: [CommonModule, FormsModule],
  templateUrl: './reservations.html',
  styleUrl: './reservations.css'
})
export class Reservations implements OnInit {
  reservations: any[] = [];
  evenements: any[] = [];
  circuits: any[] = [];
  
  errorMessage: string = '';
  successMessage: string = '';

  afficherFormulaire: boolean = false;
  reservationEnCours: any = this.videFormulaire();

  constructor(
    private service: ReservationService,
    private evenementService: EvenementService,
    private circuitService: CircuitService
  ) {}

  ngOnInit(): void {
    this.charger();
    this.chargerEvenements();
    this.chargerCircuits();
  }

  videFormulaire(): any {
    return {
      nomClient: '',
      typeCible: 'EVENEMENT',
      referenceCible: null,
      nombrePlaces: 1
    };
  }

  charger(): void {
    this.service.getAll().subscribe({
      next: data => this.reservations = data,
      error: err => {
        console.error(err);
        this.errorMessage = 'Erreur de connexion au microservice réservation';
      }
    });
  }

  chargerEvenements(): void {
    this.evenementService.getAll().subscribe({
      next: data => this.evenements = data,
      error: err => console.error('Erreur chargement événements', err)
    });
  }

  chargerCircuits(): void {
    this.circuitService.getAll().subscribe({
      next: data => this.circuits = data,
      error: err => console.error('Erreur chargement circuits', err)
    });
  }

  ouvrirFormulaireAjout(): void {
    this.reservationEnCours = this.videFormulaire();
    this.afficherFormulaire = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  fermerFormulaire(): void {
    this.afficherFormulaire = false;
    this.reservationEnCours = this.videFormulaire();
  }

  changerType(): void {
    this.reservationEnCours.referenceCible = null;
  }

  enregistrer(): void {
    if (!this.reservationEnCours.referenceCible) {
      this.errorMessage = 'Veuillez sélectionner une cible dans la liste.';
      return;
    }

    this.service.create(this.reservationEnCours).subscribe({
      next: () => {
        this.successMessage = 'Réservation créée avec succès';
        this.fermerFormulaire();
        this.charger();
      },
      error: err => {
        console.error(err);
        this.errorMessage = typeof err.error === 'string' ? err.error : 'Erreur lors de la création';
      }
    });
  }

  confirmer(id: number): void {
    this.service.confirmer(id).subscribe({
      next: () => {
        this.successMessage = 'Réservation confirmée';
        this.charger();
      },
      error: err => {
        console.error(err);
        this.errorMessage = 'Erreur lors de la confirmation';
      }
    });
  }

  annuler(id: number): void {
    this.service.annuler(id).subscribe({
      next: () => {
        this.successMessage = 'Réservation annulée';
        this.charger();
      },
      error: err => {
        console.error(err);
        this.errorMessage = "Erreur lors de l'annulation";
      }
    });
  }

  supprimer(id: number): void {
    if (confirm('Voulez-vous vraiment supprimer cette réservation ?')) {
      this.service.delete(id).subscribe({
        next: () => {
          this.successMessage = 'Réservation supprimée';
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