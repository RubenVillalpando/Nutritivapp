package mx.nutritivalabs.nutritivapp.compose.patient;

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import mx.nutritivalabs.nutritivapp.compose.Result
import mx.nutritivalabs.nutritivapp.compose.meetings.emptyMeeting
import mx.nutritivalabs.nutritivapp.compose.meetings.state.MeetingDetailState
import mx.nutritivalabs.nutritivapp.patient.Patient
import mx.nutritivalabs.nutritivapp.patient.exampleEnergyRequirements
import mx.nutritivalabs.nutritivapp.patient.examplePatient
import java.util.*
import javax.inject.Inject


class PatientViewModel
constructor(
    private val patientRepository: PatientRepository = PatientRepository()
) : ViewModel() {

    private val _state: MutableState<PatientDetailState> = mutableStateOf(PatientDetailState())
    val state: State<PatientDetailState>
        get() = _state

    private val _stateList: MutableState<PatientListDetailState> = mutableStateOf(PatientListDetailState())
    val stateList: State<PatientListDetailState>
        get() = _stateList



    fun addNewPatient(patient: Patient) {
        patientRepository.addNewPatient(patient)
    }

    fun findAll() {
        patientRepository.findAll().onEach { result ->
            when(result) {
                is Result.Error -> {
                    _stateList.value = PatientListDetailState(error = result.message  ?: "Error inesperado" )
                }
                is Result.Loading -> {
                    _stateList.value = PatientListDetailState(isLoading = true)
                }
                is Result.Success -> {
                    _stateList.value = PatientListDetailState(patients = result.data ?: listOf())
                }
            }
        }.launchIn(viewModelScope)

    }




    fun findById(id: String) {
        patientRepository.findPatientById(id).onEach { result ->
            when(result) {
                is Result.Error -> {
                    _state.value = PatientDetailState(error = result.message  ?: "Error inesperado" )
                }
                is Result.Loading -> {
                    _state.value = PatientDetailState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = PatientDetailState(patient = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}



private fun vicPatient(): Patient {
    return Patient(
        id = UUID.randomUUID().toString(),
        firstName = "Víctor",
        paternalLastName = "Sánchez",
        maternalLastName = " ",
        birthDate = Calendar.getInstance().time,
        energyRequirements = exampleEnergyRequirements().copy(calories = 2800),
        goals = listOf("Legendary", "Nutrition"),
        firstTime = false,
        email = "victor.sanchez@gmail.com",
        phoneNumber = "553648273",
        profilePictureURL = "https://static.wikia.nocookie.net/minion/images/2/27/Vector.png/revision/latest/top-crop/width/360/height/450?cb=20150724225830&path-prefix=es",
        memberSince = Calendar.getInstance().time,
        clinicData = mapOf()
    )
}

private fun arthurPatient() = Patient(
    id = UUID.randomUUID().toString(),
    firstName = "Arturo",
    paternalLastName = "Marquez",
    maternalLastName = " ",
    birthDate = Calendar.getInstance().time,
    energyRequirements = exampleEnergyRequirements().copy(calories = 2800),
    goals = listOf("Be", "Bold"),
    firstTime = false,
    email = "arturo.marquez@gmail.com",
    phoneNumber = "553648273",
    profilePictureURL = "https://i2.wp.com/revistadiners.com.co/wp-content/uploads/2017/03/loganwolverine_800x669.jpg?fit=800%2C669&ssl=1",
    memberSince = Calendar.getInstance().time,
    clinicData = mapOf()
)

private fun rubenPatient(): Patient {
    return Patient(
        id = UUID.randomUUID().toString(),
        firstName = "Rubén",
        paternalLastName = "Villalapando",
        maternalLastName = "Bremmont",
        birthDate = Calendar.getInstance().time,
        energyRequirements = exampleEnergyRequirements().copy(calories = 3000),
        goals = listOf("Be alive", "My Friend"),
        firstTime = false,
        email = "ruben.villalpando@gmail.com",
        phoneNumber = "553648273",
        profilePictureURL = "https://static.wikia.nocookie.net/middle-earth-film-saga/images/7/77/Legolas.png/revision/latest/top-crop/width/360/height/450?cb=20160207050831",
        memberSince = Calendar.getInstance().time,
        clinicData = mapOf()
    )
}
