    package extraordinariapoo;

    /**
     * Autor: [Víctor Arroyo Madera]
     * Fecha: [14/06/2023]
     */

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.ObjectInputStream;
    import java.io.ObjectOutputStream;
    import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.time.format.TextStyle;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Locale;
    import java.util.Map;

    class DateSectionScreen extends Screen {
        private Map<LocalDate, TheaterState> schedule;
        private Theater t;
        private AreaSelectionScreen as;

        public DateSectionScreen(Theater t, ScreenMode mode, DispenserManager dm) {
            super("", ScreenMode.optionsMode, dm);
            setMode(mode);
            this.t = t;
            setDispenseManager(dm);
            as = new AreaSelectionScreen("", ScreenMode.theaterMode, dm);
        }

        /**
         * Muestra el menú de selección de fechas en la pantalla.
         */
        public void Menu() {
            List<LocalDate> dates = getDatesFromToday();
            List<String> options = getOptions();
            dates.clear();
            options.clear();
            dates = getDatesFromToday();
            options = getOptions();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d", new Locale(getDispenserManager().getTranslator().getActiveIdiom()));
            this.schedule = loadStateFiles();

            for (LocalDate date : dates) {
                String dayOfMonth = date.format(formatter);
                String month = date.getMonth().getDisplayName(TextStyle.FULL, new Locale(getDispenserManager().getTranslator().getActiveIdiom()));
                String day = date.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale(getDispenserManager().getTranslator().getActiveIdiom()));
                String dateString = dayOfMonth + " " + day + " " + month;
                options.add(dateString);
            }
            setOptions(options);

            super.getDispenserManager().getDispenser().setTitle(java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Fecha"));
            super.getDispenserManager().getDispenser().setOption(0, options.get(0));
            super.getDispenserManager().getDispenser().setOption(1, options.get(1));
            super.getDispenserManager().getDispenser().setOption(2, options.get(2));
            super.getDispenserManager().getDispenser().setOption(3, options.get(3));
            super.getDispenserManager().getDispenser().setOption(4, options.get(4));
            super.getDispenserManager().getDispenser().setOption(5, java.util.ResourceBundle.getBundle("extraordinariapoo/" + super.getDispenserManager().getTranslator().getActiveIdiom()).getString("Cancelar"));
            getDispenserManager().getDispenser().setDescription("");
            getDispenserManager().getDispenser().setImage("ConfigFilesExamples/imgReyLeon.png");
        }

        /**
         * Maneja el evento de presionar un botón de opción en la pantalla.
         * 
         * @param dh El hardware del dispensador
         * @param c El carácter del botón presionado
         * @return El resultado de la pantalla
         */
        public ScreenResult optionButtonPressed(DispenserHardware dh, char c) {
            as.setSchedule(schedule);
            switch (c) {
                case 'A':
                    as.setDateChoose(getDatesFromToday().get(0));
                    getDispenserManager().showScreen(30, as);
                    return ScreenResult.exitScreen;
                case 'B':
                    as.setDateChoose(getDatesFromToday().get(1));
                    getDispenserManager().showScreen(30, as);
                    return ScreenResult.exitScreen;
                case 'C':
                    as.setDateChoose(getDatesFromToday().get(2));
                    getDispenserManager().showScreen(30, as);
                    return ScreenResult.exitScreen;
                case 'D':
                    as.setDateChoose(getDatesFromToday().get(3));
                    getDispenserManager().showScreen(30, as);
                    return ScreenResult.exitScreen;
                case 'E':
                    as.setDateChoose(getDatesFromToday().get(4));
                    getDispenserManager().showScreen(30, as);
                    return ScreenResult.exitScreen;
                case 'F':
                    return ScreenResult.exitScreen;
            }
            return ScreenResult.exitScreen;
        }

        /**
         * Carga los archivos de estado guardados y devuelve el mapa de programación de teatro.
         * Si no hay archivos de estado, crea un nuevo mapa con las fechas actuales.
         * 
         * @return El mapa de programación de teatro cargado o creado
         */
        public Map<LocalDate, TheaterState> loadStateFiles() {
            List<LocalDate> dates = getDatesFromToday();
            String filename = "data/Storage.bin";
            File file = new File(filename);
            Map<LocalDate, TheaterState> schedule;
            if (file.exists()) {
                try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(file))) {
                    schedule = (Map<LocalDate, TheaterState>) entrada.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    schedule = new HashMap<>();
                }
            } else {
                schedule = new HashMap<>();
                for (LocalDate date : dates) {
                    schedule.put(date, new TheaterState(t, date));
                }
            }
            for (LocalDate date : dates) {
                if (!schedule.containsKey(date)) {
                    schedule.put(date, new TheaterState(t, date));
                }
            }
            try (FileOutputStream fos = new FileOutputStream(filename);
                    ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(schedule);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return schedule;
        }

        /**
         * Obtiene una lista de fechas a partir de la fecha actual.
         * Se generan cinco fechas consecutivas excluyendo los lunes.
         * 
         * @return La lista de fechas generadas
         */
        public List<LocalDate> getDatesFromToday() {
            List<LocalDate> dates = new ArrayList<>();
            LocalDate today = LocalDate.now();

            int daysToAdd = 0;
            while (dates.size() < 5) {
                LocalDate date = today.plusDays(daysToAdd);
                if (date.getDayOfWeek() != DayOfWeek.MONDAY) {
                    dates.add(date);
                }
                daysToAdd++;
            }

            return dates;
        }

        public String getTitle() {
            return super.getTitle();
        }
    }
