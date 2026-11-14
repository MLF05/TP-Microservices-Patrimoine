import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PatrimoineService } from '../../services/patrimoine.service';

@Component({
  selector: 'app-patrimoines',
  imports: [CommonModule, FormsModule],
  templateUrl: './patrimoines.html',
  styleUrl: './patrimoines.css'
})
export class Patrimoines implements OnInit {
  patrimoines: any[] = [];
  chargement: boolean = true;
  recherche: string = '';
  errorMessage: string = '';
  successMessage: string = '';

  // État du formulaire
  afficherFormulaire: boolean = false;
  modeEdition: boolean = false;
  patrimoineEnCours: any = this.videFormulaire();

  constructor(private service: PatrimoineService) {}

  ngOnInit(): void {
    this.charger();
  }

  get patrimoinesFiltres(): any[] {
    const q = this.recherche.trim().toLowerCase();
    if (!q) return this.patrimoines;
    return this.patrimoines.filter(p =>
      [p.nom, p.code, p.adresse, p.description].some(v => (v ?? '').toLowerCase().includes(q)));
  }

  videFormulaire(): any {
    return {
      id: null,
      nom: '',
      code: '',
      description: '',
      historique: '',
      adresse: '',
      capacite: 0
    };
  }

  charger(): void {
    this.service.getAll().subscribe({
      next: data => { this.patrimoines = data; this.chargement = false; },
      error: err => {
        console.error(err);
        this.chargement = false;
        this.errorMessage = 'Erreur de connexion au microservice patrimoine';
      }
    });
  }

  ouvrirFormulaireAjout(): void {
    this.patrimoineEnCours = this.videFormulaire();
    this.modeEdition = false;
    this.afficherFormulaire = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  ouvrirFormulaireModif(p: any): void {
    this.patrimoineEnCours = { ...p };
    this.modeEdition = true;
    this.afficherFormulaire = true;
    this.errorMessage = '';
    this.successMessage = '';
  }

  fermerFormulaire(): void {
    this.afficherFormulaire = false;
    this.patrimoineEnCours = this.videFormulaire();
  }

  enregistrer(): void {
    if (this.modeEdition) {
      this.service.update(this.patrimoineEnCours.id, this.patrimoineEnCours).subscribe({
        next: () => {
          this.successMessage = 'Patrimoine modifié avec succès';
          this.fermerFormulaire();
          this.charger();
        },
        error: err => {
          console.error(err);
          this.errorMessage = 'Erreur lors de la modification';
        }
      });
    } else {
      this.service.create(this.patrimoineEnCours).subscribe({
        next: () => {
          this.successMessage = 'Patrimoine ajouté avec succès';
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
    if (confirm('Voulez-vous vraiment supprimer ce patrimoine ?')) {
      this.service.delete(id).subscribe({
        next: () => {
          this.successMessage = 'Patrimoine supprimé';
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