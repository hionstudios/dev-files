let $ndJSON = {
    stream: async (url, callback, options) => {
        let resp = await fetcher(url, options);
        $ndJSON.readFromReader(resp.body.getReader(), callback);
        return resp;
    },
    readFromReader(reader, callback) {
        reader.read().then(({value, done}) => {
            if (!done) {
                if (value !== "\n") {
                    if (callback) $ndJSON.parseData(value, callback);
                }
                $ndJSON.readFromReader(reader, callback);
            }
        });
    },
    parseData(data, callback) {
        data = new TextDecoder("utf-8").decode(data);
        let array = data.split("\n");
        for (let i = 0; i < array.length; i++) {
            if (array[i]) {
                callback(this.ifJson(array[i]));
            }
        }
    },
    ifJson(data) {
        try {
            return JSON.parse(data);
        } catch (e) {
            return data;
        }
    }
}