package com.example.womensafetyapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.womensafetyapp.network.ApiService
import com.example.womensafetyapp.network.CreateContactRequest
import com.example.womensafetyapp.network.NetworkClient
import com.example.womensafetyapp.network.NetworkResult
import com.example.womensafetyapp.network.UpdateContactRequest
import com.example.womensafetyapp.network.safeApiCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EmergencyContact(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val phoneNumber: String,
    val relationship: String
)

class ContactsRepository(
    private val apiService: ApiService = NetworkClient.apiService
) {
    private val _contacts = MutableStateFlow<List<EmergencyContact>>(emptyList())
    val contacts: StateFlow<List<EmergencyContact>> = _contacts.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    // Auth token - in real app, this would come from auth manager
    private var authToken: String? = null
    
    init {
        // Load default contacts for demo purposes
        // In real app, you would call fetchContacts() here
        loadDefaultContactsForDemo()
    }
    
    // TODO: Remove this method when backend is ready
    private fun loadDefaultContactsForDemo() {
        _contacts.value = listOf(
            EmergencyContact(
                name = "Mom",
                phoneNumber = "+1 (555) 123-4567",
                relationship = "Mother"
            ),
            EmergencyContact(
                name = "Best Friend Sarah",
                phoneNumber = "+1 (555) 987-6543",
                relationship = "Friend"
            ),
            EmergencyContact(
                name = "Dad",
                phoneNumber = "+1 (555) 234-5678",
                relationship = "Father"
            ),
            EmergencyContact(
                name = "Sister Emma",
                phoneNumber = "+1 (555) 345-6789",
                relationship = "Sister"
            )
        )
    }
    
    // Fetch contacts from API
    suspend fun fetchContacts(): NetworkResult<List<EmergencyContact>> {
        _isLoading.value = true
        _error.value = null
        
        return when (val result = safeApiCall { apiService.getEmergencyContacts(authToken) }) {
            is NetworkResult.Success -> {
                _contacts.value = result.data.data
                _isLoading.value = false
                NetworkResult.Success(result.data.data)
            }

            is NetworkResult.Error -> {
                _error.value = result.message
                _isLoading.value = false
                result
            }

            is NetworkResult.Loading -> result
        } as NetworkResult<List<EmergencyContact>>
    }
    
    // Add contact via API
    suspend fun addContact(name: String, phoneNumber: String, relationship: String): NetworkResult<EmergencyContact> {
        _isLoading.value = true
        _error.value = null
        
        val request = CreateContactRequest(name, phoneNumber, relationship)
        
        return when (val result = safeApiCall { apiService.addEmergencyContact(authToken, request) }) {
            is NetworkResult.Success -> {
                // Update local list
                val currentContacts = _contacts.value.toMutableList()
                currentContacts.add(result.data)
                _contacts.value = currentContacts
                _isLoading.value = false
                result
            }
            is NetworkResult.Error -> {
                _error.value = result.message
                _isLoading.value = false
                result
            }
            is NetworkResult.Loading -> result
        }
    }
    
    // Add contact locally (for demo without backend)
    fun addContactLocally(name: String, phoneNumber: String, relationship: String) {
        val newContact = EmergencyContact(
            name = name,
            phoneNumber = phoneNumber,
            relationship = relationship
        )
        val currentContacts = _contacts.value.toMutableList()
        currentContacts.add(newContact)
        _contacts.value = currentContacts
    }
    
    // Update contact via API
    suspend fun updateContact(contactId: String, name: String, phoneNumber: String, relationship: String): NetworkResult<EmergencyContact> {
        _isLoading.value = true
        _error.value = null
        
        val request = UpdateContactRequest(name, phoneNumber, relationship)
        
        return when (val result = safeApiCall { apiService.updateEmergencyContact(contactId, authToken, request) }) {
            is NetworkResult.Success -> {
                // Update local list
                val currentContacts = _contacts.value.toMutableList()
                val index = currentContacts.indexOfFirst { it.id == contactId }
                if (index != -1) {
                    currentContacts[index] = result.data
                    _contacts.value = currentContacts
                }
                _isLoading.value = false
                result
            }
            is NetworkResult.Error -> {
                _error.value = result.message
                _isLoading.value = false
                result
            }
            is NetworkResult.Loading -> result
        }
    }
    
    // Delete contact via API
    suspend fun removeContact(contact: EmergencyContact): NetworkResult<Unit> {
        _isLoading.value = true
        _error.value = null
        
        return when (val result = safeApiCall { apiService.deleteEmergencyContact(contact.id, authToken) }) {
            is NetworkResult.Success -> {
                // Update local list
                val currentContacts = _contacts.value.toMutableList()
                currentContacts.remove(contact)
                _contacts.value = currentContacts
                _isLoading.value = false
                result
            }
            is NetworkResult.Error -> {
                _error.value = result.message
                _isLoading.value = false
                result
            }
            is NetworkResult.Loading -> result
        }
    }
    
    // Remove contact locally (for demo without backend)
    fun removeContactLocally(contact: EmergencyContact) {
        val currentContacts = _contacts.value.toMutableList()
        currentContacts.remove(contact)
        _contacts.value = currentContacts
    }
    
    // Get contact by ID
    fun getContactById(id: String): EmergencyContact? {
        return _contacts.value.find { it.id == id }
    }
    
    // Get contacts by relationship type
    fun getContactsByRelationship(relationship: String): List<EmergencyContact> {
        return _contacts.value.filter { it.relationship.equals(relationship, ignoreCase = true) }
    }
    
    // Get primary emergency contacts (first 3)
    fun getPrimaryEmergencyContacts(): List<EmergencyContact> {
        return _contacts.value.take(3)
    }
    
    // Clear error
    fun clearError() {
        _error.value = null
    }
    
    // Set auth token
    fun setAuthToken(token: String?) {
        authToken = token
    }
}

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {
    val contacts = repository.contacts
    val isLoading = repository.isLoading
    val error = repository.error

    // For demo without backend - use local methods
    fun addContact(name: String, phoneNumber: String, relationship: String) {
        repository.addContactLocally(name, phoneNumber, relationship)
    }

    fun removeContact(contact: EmergencyContact) {
        repository.removeContactLocally(contact)
    }

    // For real API calls - use these methods in coroutine scope
    suspend fun addContactApi(name: String, phoneNumber: String, relationship: String): NetworkResult<EmergencyContact> {
        return repository.addContact(name, phoneNumber, relationship)
    }

    suspend fun removeContactApi(contact: EmergencyContact): NetworkResult<Unit> {
        return repository.removeContact(contact)
    }

    suspend fun fetchContacts(): NetworkResult<List<EmergencyContact>> {
        return repository.fetchContacts()
    }

    suspend fun updateContact(contactId: String, name: String, phoneNumber: String, relationship: String): NetworkResult<EmergencyContact> {
        return repository.updateContact(contactId, name, phoneNumber, relationship)
    }

    fun getPrimaryContacts() = repository.getPrimaryEmergencyContacts()
    
    fun clearError() = repository.clearError()
    
    fun setAuthToken(token: String?) = repository.setAuthToken(token)
}

class ContactsViewModelFactory(private val repository: ContactsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}