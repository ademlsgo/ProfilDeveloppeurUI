package com.example.profildeveloppeurui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profildeveloppeurui.ui.theme.ProfilDeveloppeurTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfilDeveloppeurTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileScreen(
                        profile = sampleAndroidProfile(),
                        onContactClick = {
                            // Plus tard : ouvrir l'application mail
                        },
                        onPortfolioClick = {
                            // Plus tard : ouvrir le portfolio
                        },
                        onAvailabilityClick = {
                            // Plus tard : afficher le détail de la disponibilité
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// =====================================================================
// Données locales
// =====================================================================

data class ProjectUi(
    val id: Int,
    val title: String,
    val description: String,
    val technologies: String
)

data class DeveloperProfile(
    val name: String,
    val role: String,
    val description: String,
    val skills: List<String>,
    val projects: List<ProjectUi>,
    val email: String,
    val location: String,
    val availability: String
)

fun sampleAndroidProfile(): DeveloperProfile {
    return DeveloperProfile(
        name = "Adem Fellah",
        role = "Développeur Android junior",
        description = "Étudiant en informatique passionné par le mobile. " +
                "Je crée des applications Android modernes avec Kotlin et Jetpack Compose.",
        skills = listOf("Kotlin", "Jetpack Compose", "Material 3", "Git / GitHub", "Android Studio"),
        projects = listOf(
            ProjectUi(
                id = 1,
                title = "Product Explorer",
                description = "Catalogue de produits avec écran d'accueil, liste et thème clair / sombre.",
                technologies = "Compose • LazyColumn • Material 3"
            ),
            ProjectUi(
                id = 2,
                title = "Profil développeur",
                description = "Fiche de profil construite avec des composables réutilisables.",
                technologies = "Compose • Modifier • Previews"
            ),
            ProjectUi(
                id = 3,
                title = "Snake",
                description = "Petit jeu du serpent réalisé pour apprendre la logique de jeu.",
                technologies = "Kotlin"
            )
        ),
        email = "adem.fellah@email.com",
        location = "Paris, France",
        availability = "Disponible pour une alternance"
    )
}

fun sampleFullstackProfile(): DeveloperProfile {
    return DeveloperProfile(
        name = "Ines Damir",
        role = "Développeuse fullstack freelance",
        description = "Je conçois des applications web de bout en bout, de l'interface jusqu'à la base de données.",
        skills = listOf("TypeScript", "React", "Node.js", "PostgreSQL", "Docker"),
        projects = listOf(
            ProjectUi(
                id = 1,
                title = "Plateforme de réservation",
                description = "Application web de réservation de salles pour une école.",
                technologies = "React • Node.js • PostgreSQL"
            ),
            ProjectUi(
                id = 2,
                title = "Dashboard e-commerce",
                description = "Tableau de bord des ventes en temps réel.",
                technologies = "TypeScript • Docker"
            )
        ),
        email = "ines.damir@email.com",
        location = "Lyon, France",
        availability = "Disponible en freelance dès janvier"
    )
}

// =====================================================================
// Écran principal
// =====================================================================

/**
 * Écran complet : une LazyColumn qui contient des sections uniques (item)
 * et la liste des projets (items).
 */
@Composable
fun ProfileScreen(
    profile: DeveloperProfile,
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
    onAvailabilityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileHeader(
                name = profile.name,
                role = profile.role
            )
        }
        item {
            ProfileDescription(description = profile.description)
        }
        item {
            Column {
                SectionTitle(title = "Compétences")
                Spacer(modifier = Modifier.height(8.dp))
                SkillRow(skills = profile.skills)
            }
        }
        item {
            SectionTitle(title = "Projets")
        }
        items(
            items = profile.projects,
            key = { project -> project.id }
        ) { project ->
            ProjectCard(project = project)
        }
        item {
            ContactCard(
                email = profile.email,
                location = profile.location
            )
        }
        item {
            AvailabilityBanner(
                availability = profile.availability,
                onClick = onAvailabilityClick
            )
        }
        item {
            ProfileActions(
                onContactClick = onContactClick,
                onPortfolioClick = onPortfolioClick
            )
        }
    }
}

// =====================================================================
// En-tête : avatar + badge (Box) et identité
// =====================================================================

@Composable
fun ProfileHeader(
    name: String,
    role: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileAvatar(name = name)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = role,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

/**
 * Box : l'avatar et le badge "Dispo" sont superposés dans la même zone.
 */
@Composable
fun ProfileAvatar(
    name: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(88.dp)) {
        Surface(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar de $name",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer),
                modifier = Modifier.padding(12.dp)
            )
        }
        Surface(
            modifier = Modifier.align(Alignment.BottomEnd),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ) {
            Text(
                text = "Dispo",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
fun ProfileDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

// =====================================================================
// Compétences : LazyRow + SkillChip réutilisable
// =====================================================================

@Composable
fun SkillRow(
    skills: List<String>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(skills) { skill ->
            SkillChip(skill = skill)
        }
    }
}

@Composable
fun SkillChip(
    skill: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
    ) {
        Text(
            text = skill,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

// =====================================================================
// Projets : ProjectCard réutilisable (appelée par items de la LazyColumn)
// =====================================================================

@Composable
fun ProjectCard(
    project: ProjectUi,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(
                imageVector = Icons.Default.Code,
                contentDescription = null, // décorative : le titre suffit
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = project.technologies,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

// =====================================================================
// Contact : Card + OutlinedTextField en lecture seule + InfoRow
// =====================================================================

@Composable
fun ContactCard(
    email: String,
    location: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Contact",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Email") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                text = location,
                iconDescription = "Localisation"
            )
        }
    }
}

@Composable
fun InfoRow(
    text: String,
    iconDescription: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = iconDescription,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// =====================================================================
// Disponibilité : zone cliquable personnalisée et accessible
// =====================================================================

@Composable
fun AvailabilityBanner(
    availability: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = "Voir les détails de la disponibilité",
                role = Role.Button,
                onClick = onClick
            )
            .semantics {
                contentDescription = "$availability. Bouton : voir les détails"
            },
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        Text(
            text = "✅ $availability",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// =====================================================================
// Actions : deux boutons côte à côte (Row + weight)
// =====================================================================

@Composable
fun ProfileActions(
    onContactClick: () -> Unit,
    onPortfolioClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onContactClick,
            modifier = Modifier.weight(1f)
        ) {
            Icon(imageVector = Icons.Default.Email, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Contacter")
        }
        Spacer(modifier = Modifier.width(12.dp))
        OutlinedButton(
            onClick = onPortfolioClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Portfolio")
        }
    }
}

// =====================================================================
// Previews : thème clair, thème sombre et second profil
// =====================================================================

@Preview(showBackground = true, name = "Profil Android - Thème clair")
@Composable
fun ProfileScreenLightPreview() {
    ProfilDeveloppeurTheme(darkTheme = false) {
        ProfileScreen(
            profile = sampleAndroidProfile(),
            onContactClick = {},
            onPortfolioClick = {},
            onAvailabilityClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Profil Android - Thème sombre")
@Composable
fun ProfileScreenDarkPreview() {
    ProfilDeveloppeurTheme(darkTheme = true) {
        ProfileScreen(
            profile = sampleAndroidProfile(),
            onContactClick = {},
            onPortfolioClick = {},
            onAvailabilityClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Profil fullstack - Thème clair")
@Composable
fun ProfileScreenFullstackPreview() {
    ProfilDeveloppeurTheme(darkTheme = false) {
        ProfileScreen(
            profile = sampleFullstackProfile(),
            onContactClick = {},
            onPortfolioClick = {},
            onAvailabilityClick = {}
        )
    }
}
