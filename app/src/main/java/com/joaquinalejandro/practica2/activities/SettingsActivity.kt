package com.joaquinalejandro.practica2.activities

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.preference.RingtonePreference
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import com.joaquinalejandro.practica2.R

/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 */
class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        fragmentManager.beginTransaction().replace(
            android.R.id.content,
            GeneralPreferenceFragment()
        ).commit()
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * {@inheritDoc}
     */
    override fun onIsMultiPane(): Boolean {
        return isXLargeTablet(this)
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        //loadHeadersFromResource(R.xml.pref_headers, target)
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName
                || GeneralPreferenceFragment::class.java.name == fragmentName
                || DataSyncPreferenceFragment::class.java.name == fragmentName
                || NotificationPreferenceFragment::class.java.name == fragmentName
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class GeneralPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_general)
            setHasOptionsMenu(true)

            /*val preference = findPreference("prueba")
            preference.onPreferenceClickListener = Preference.OnPreferenceClickListener()
            {
                Toast.makeText(activity, "Preferencia pulsada", Toast.LENGTH_LONG).show()
                true
            }*/

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.

            bindPreferenceSummaryToValue(findPreference("TABLERO_KEY"))
            bindPreferenceSummaryToValue(findPreference("FICHAS_KEY"))

            val color_tab = findPreference("TABLERO_KEY")
            val color_fich = findPreference("FICHAS_KEY")

            println(color_tab)
            println(color_fich)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * This fragment shows notification preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class NotificationPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_notification)
            setHasOptionsMenu(true)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"))

        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }

    /**
     * This fragment shows data and sync preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class DataSyncPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_data_sync)
            setHasOptionsMenu(true)

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("sync_frequency"))
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }
    }


    companion object {

        val COLOR_TABLERO_KEY = "TABLERO_KEY"
        val COLOR_FICHAS_KEY = "FICHAS_KEY"

        val COLOR_TABLERO_DEFAULT = "Azul"
        val COLOR_FICHAS_DEFAULT = "Rojo/Amarillo"

        fun getColorTablero(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(COLOR_TABLERO_KEY, COLOR_TABLERO_DEFAULT)
        }

        fun getColorFichas(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(COLOR_FICHAS_KEY, COLOR_FICHAS_DEFAULT)
        }

        fun setColorTablero(context: Context, color:String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.COLOR_TABLERO_KEY, color)
            editor.commit()
        }

        fun setColorFichas(context: Context, color:String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.COLOR_FICHAS_KEY, color)
            editor.commit()
        }

        /*val TABLERO_COLUMNAS_KEY = "num_filas"
        val TABLERO_COLUMNAS_DEFAULT = "6"

        val TABLERO_FILAS_KEY = "num_columnas"
        val TABLERO_FILAS_DEFAULT = "7"



        fun getTableroColumnas(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TABLERO_COLUMNAS_KEY, TABLERO_COLUMNAS_DEFAULT)
        }

        fun getTableroFilas(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TABLERO_FILAS_KEY, TABLERO_FILAS_DEFAULT)
        }


        fun setTableroColumnas(context: Context, size: Int) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.TABLERO_COLUMNAS_KEY, size)
            editor.commit()
        }

        fun setTableroFilas(context: Context, size: Int) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.TABLERO_FILAS_KEY, size)
            editor.commit()
        }*/


        val TABLERO_NAME_KEY = "player_name"
        val TABLERO_NAME_DEFAULT = ""

        val TABLERO_UUID_KEY = "uuid_name"
        val TABLERO_UUID_DEFAULT = ""

        val DESCRIPCION = "desc"
        val DESCRIPCION_DEFAULT = ""

        fun getDescripcion(context: Context) :String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString( DESCRIPCION, DESCRIPCION_DEFAULT)
        }

        fun setDescripcion(context: Context, id: String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.DESCRIPCION, id)
            editor.commit()
        }

        fun getPlayerUUID(context: Context) :String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString( TABLERO_UUID_KEY, TABLERO_UUID_DEFAULT)
        }

        fun getPlayerName(context: Context):String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(TABLERO_NAME_KEY, TABLERO_NAME_DEFAULT)
        }

        fun setPlayerUUID(context: Context, id: String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.TABLERO_UUID_KEY, id)
            editor.commit()
        }

        fun setPlayerName(context: Context, email: String) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(SettingsActivity.TABLERO_NAME_KEY, email)
            editor.commit()
        }

        val TIPO_BD_KEY = "tipo_bd"
        val TIPO_BD_DEFAULT = "LOCAL" //LOCAL FIREBASE

        fun setTipoBD(context:Context,tipo:String){
            if(tipo=="LOCAL" || tipo=="FIREBASE") {
                val sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(context)
                val editor = sharedPreferences.edit()
                editor.putString(SettingsActivity.TIPO_BD_KEY, tipo)
                editor.commit()
            }else{
                println("Error en el tipo de base de datos")
                return
            }
        }
        fun getTipoBd(context:Context):String{
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString( TIPO_BD_KEY, TIPO_BD_DEFAULT)
        }

        val TURNO_KEY = "TURNO_KEY"
        val TURNO_DEFAULT = 0 //LOCAL FIREBASE

        fun setTurno(context:Context,tipo:Int){

            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.TURNO_KEY, tipo)
            editor.commit()

        }
        fun getTurno(context:Context):Int{
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt( TURNO_KEY, TURNO_DEFAULT)
        }

        /**
         * A preference value change listener that updates the preference's summary
         * to reflect its new value.
         */

        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, value ->
            val stringValue = value.toString()

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val listPreference = preference
                val index = listPreference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                    if (index >= 0)
                        listPreference.entries[index]
                    else
                        null
                )

            } else if (preference is RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent)

                } else {
                    val ringtone = RingtoneManager.getRingtone(
                        preference.getContext(), Uri.parse(stringValue)
                    )

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null)
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        val name = ringtone.getTitle(preference.getContext())
                        preference.setSummary(name)
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.summary = stringValue
            }
            true
        }

        /**
         * Binds a preference's summary to its value. More specifically, when the
         * preference's value is changed, its summary (line of text below the
         * preference title) is updated to reflect the value. The summary is also
         * immediately updated upon calling this method. The exact display format is
         * dependent on the type of preference.

         * @see .sBindPreferenceSummaryToValueListener
         */
        private fun bindPreferenceSummaryToValue(preference: Preference) {
            // Set the listener to watch for value changes.
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            // Trigger the listener immediately with the preference's
            // current value.
            sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, "")
            )
        }
        val BOARDSIZE_KEY = "boardsize_list"
        val BOARDSIZE_DEFAULT = "3"
        fun getBoardSize(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(BOARDSIZE_KEY, BOARDSIZE_DEFAULT)
        }
        fun setBoardsize(context: Context, size: Int) {
            val sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(SettingsActivity.BOARDSIZE_KEY, size)
            editor.commit()
        }
        /**
         * Helper method to determine if the device has an extra-large screen. For
         * example, 10" tablets are extra-large.
         */
        private fun isXLargeTablet(context: Context): Boolean {
            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
        }
    }
}
