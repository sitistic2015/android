from flask import Flask, jsonify, request
from flask_restful import abort

app = Flask(__name__)

waypoints = [];

@app.route('/robot/rotate', methods=['POST'])
def get_waypoint() :
    if not request.json or not 'd' in request.json:
        abort(400)
    
    d = request.json['x']
    
    return 204

@app.route('/robot/waypoint', methods=['POST'])
def append_waypoint():
    if not request.json or not 'x' in request.json or not 'y' in request.json or not 'z' in request.json:
        abort(400)
    
    x = request.json['x']
    y = request.json['y']
    z = request.json['z']
    
    return 204

if __name__ == '__main__' :
    app.run(debug=True)